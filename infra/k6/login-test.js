import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
  vus: 10,
  duration: '20s',
  thresholds: {
    http_req_failed: ['rate<0.01'],
    http_req_duration: ['p(95)<500'],
  },
};

const BASE_URL = 'http://localhost:8080';

export default function () {

  const payload = JSON.stringify({
    email: 'ci@test.com',
    senha: '123456',
  });

  const res = http.post(`${BASE_URL}/auth/login`, payload, {
    headers: { 'Content-Type': 'application/json' },
  });

  const body = safeJson(res);

  const token = body?.data?.token || body?.token;

  check(res, {
    'status 200': (r) => r.status === 200,
    'token existe': () => token !== undefined && token !== null,
    'tempo < 500ms': (r) => r.timings.duration < 500,
  });

  sleep(1);
}

function safeJson(res) {
  try {
    return res.json();
  } catch (e) {
    return {};
  }
}