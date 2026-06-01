import http from 'k6/http';
import { check, sleep } from 'k6';

const BASE_URL = 'http://localhost:8080';

const CATEGORY_ID = Number(__ENV.CATEGORY_ID);

export default function () {

  if (!CATEGORY_ID) {
    throw new Error('CATEGORY_ID inválido');
  }

  const payload = JSON.stringify({
    nome: `Produto-${__VU}-${__ITER}`,
    quantidade: 10,
    preco: 199.90,
    status: 'ATIVO',
    categoria_id: CATEGORY_ID,
  });

  const res = http.post(`${BASE_URL}/produtos`, payload, {
    headers: { 'Content-Type': 'application/json' },
  });

  check(res, {
    'status 201': (r) => r.status === 201,
    'tempo < 500ms': (r) => r.timings.duration < 500,
  });

  sleep(1);
}