import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {

    stages: [
        { duration: '20s', target: 20 },
        { duration: '30s', target: 50 },
        { duration: '20s', target: 100 },
        { duration: '10s', target: 0 },
    ],

    thresholds: {
        http_req_duration: ['p(95)<1000'],
        http_req_failed: ['rate<0.05'],
    },
};

const BASE_URL = 'http://localhost:8080';

export default function () {

    const response = http.get(`${BASE_URL}/produtos`);

    check(response, {
        'status 200': (r) => r.status === 200,
        'tempo resposta < 1000ms': (r) => r.timings.duration < 1000,
    });

    sleep(1);
}