import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
    vus: 10,
    duration: '20s',
};

const BASE_URL = 'http://localhost:8080';

export default function () {

    const payload = JSON.stringify({
        email: 'admin@email.com',
        senha: '123456'
    });

    const params = {
        headers: {
            'Content-Type': 'application/json',
        },
    };

    const response = http.post(
        `${BASE_URL}/auth/login`,
        payload,
        params
    );

    check(response, {
        'login com sucesso': (r) => r.status === 200,
        'token retornado': (r) => r.body.includes('token'),
    });

    sleep(1);
}