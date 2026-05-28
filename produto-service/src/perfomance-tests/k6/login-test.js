import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
    vus: 10,
    duration: '30s',

    thresholds: {
        http_req_failed: ['rate<0.01'],
        http_req_duration: ['p(95)<500'],
    },
};

export default function () {

    const payload = JSON.stringify({
        email: 'silvio@email.com',
        senha: '123456',
    });

    const params = {
        headers: {
            'Content-Type': 'application/json',
        },
    };

    // LOGIN
    const res = http.post(
        'http://localhost:8080/auth/login',
        payload,
        params
    );

    console.log('STATUS:', res.status);
    console.log('BODY:', res.body);

    let body = {};

    try {
        body = res.json();
    } catch (e) {
        console.log('Erro ao converter JSON');
    }

    // TOKEN
    const token =
        body.data &&
        body.data.token;

    console.log('TOKEN:', token);

    check(res, {
        'status 200': (r) => r.status === 200,

        'tem message': () =>
            body.message !== undefined,

        'token existe': () =>
            token !== undefined &&
            token !== null &&
            token.length > 0,

        'tempo resposta < 500ms': (r) =>
            r.timings.duration < 500,
    });

    sleep(1);
}