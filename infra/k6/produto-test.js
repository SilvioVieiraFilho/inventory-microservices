import http from 'k6/http';
import { check, sleep } from 'k6';

const BASE_URL = 'http://localhost:8080';

const CATEGORY_ID = parseInt(__ENV.CATEGORY_ID, 10);

export default function () {

  if (!CATEGORY_ID || isNaN(CATEGORY_ID)) {
    throw new Error(`CATEGORY_ID inválido: ${__ENV.CATEGORY_ID}`);
  }

  const payload = JSON.stringify({
    nome: `Produto-${__VU}-${__ITER}`,
    quantidade: 10,
    preco: 199.90,
    status: 'ATIVO',
    categoriaId: CATEGORY_ID,
  });

  const res = http.post(`${BASE_URL}/produtos`, payload, {
    headers: { 'Content-Type': 'application/json' },
  });

  if (res.status !== 201) {
    console.log('ERRO:', res.status, res.body);
  }

  check(res, {
    'status 201': (r) => r.status === 201,
    'tempo < 500ms': (r) => r.timings.duration < 500,
  });

  sleep(1);
}