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
const CATEGORY_ID = Number(__ENV.CATEGORY_ID);

export default function () {

    if (!CATEGORY_ID || CATEGORY_ID <= 0) {
        throw new Error("CATEGORY_ID inválido vindo do CI");
    }

    const payload = JSON.stringify({
        nome: `Produto-${__VU}-${__ITER}`,
        quantidade: 10,
        preco: 199.90,
        status: 'ATIVO',
        categoria_id: CATEGORY_ID
    });

    const res = http.post(`${BASE_URL}/produtos`, payload, {
        headers: { 'Content-Type': 'application/json' }
    });

    console.log('STATUS:', res.status);
    console.log('BODY:', res.body);

    check(res, {
        'status 201': (r) => r.status === 201,
        'tempo < 500ms': (r) => r.timings.duration < 500,
    });

    sleep(1);
}