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

export default function () {

    const payload = JSON.stringify({
        nome: `Produto-${__VU}-${__ITER}`,
        quantidade: 10,
        preco: 199.90,
        status: 'ATIVO',
        categoria_id: 1
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

    console.log('STATUS:', response.status);
    console.log('BODY:', response.body);

    const body = response.json();

    check(response, {
        'status 201': (r) => r.status === 201,
        'tem message': () => body.message !== undefined,
        'tempo resposta < 500ms': (r) => r.timings.duration < 500,
    });

    sleep(1);
}