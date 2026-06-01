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

// 🔥 categoria vinda do CI
const CATEGORY_ID = __ENV.CATEGORY_ID;

// 🔥 validação crítica
if (!CATEGORY_ID) {
    throw new Error('CATEGORY_ID não definido no ambiente (CI)');
}

export default function () {

    const payload = JSON.stringify({
        nome: `Produto-${__VU}-${__ITER}`,
        quantidade: 10,
        preco: 199.90,
        status: 'ATIVO',
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
    } catch (e) {}

    check(response, {
        'status 201': (r) => r.status === 201,
        'tempo resposta < 500ms': (r) => r.timings.duration < 500,
    });

    sleep(1);
}