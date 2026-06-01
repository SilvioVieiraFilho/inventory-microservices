import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
  stages: [
    { duration: '20s', target: 20 },
    { duration: '30s', target: 50 },
    { duration: '20s', target: 100 },
    { duration: '10s', target: 0 },
  ],
};

const BASE_URL = 'http://localhost:8080';

export default function () {
  const res = http.get(`${BASE_URL}/produtos`);

  check(res, {
    'status 200': (r) => r.status === 200,
    'tempo < 1s': (r) => r.timings.duration < 1000,
  });

  sleep(1);
}