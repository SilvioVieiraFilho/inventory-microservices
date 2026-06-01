import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
    vus: 20,
    duration: '30s',

    thresholds: {
        http_req_duration: ['p(95)<500'],
        http_req_failed: ['rate<0.01'],
    },
};

const BASE_URL = 'http://localhost:8080';

// 🔥 pega do CI
const CATEGORY_ID = __ENV.CATEGORY_ID;

export default function () {

    const payload = JSON.stringify({
        nome: `Produto-${__VU}-${__ITER}`,
        quantidade: 10,
        preco: 199.90,
        status: 'ATIVO',

        // 🔥 AQUI está a correção
        categoria_id: Number(CATEGORY_ID)
    });

    const params = {
        headers: {
            'Content-Type': 'application/json',
        },
    };

    const response = http.post(
        `${BASE_URL}/produtos`,
        payload,
        params
    );

    let body = {};
    try {
        body = response.json();
    } catch (e) {
        console.log('Erro parsing JSON');
    }

    console.log('STATUS:', response.status);
    console.log('BODY:', response.body);

    check(response, {
        'status 201': (r) => r.status === 201,
        'tempo resposta < 500ms': (r) => r.timings.duration < 500,
    });

    sleep(1);
}