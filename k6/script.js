import http from 'k6/http';
import {check} from 'k6';

export const options = {
  vus: 10,
  duration: '30s',
};

function buildCreatePaymentRequest() {
  return {
    user: {
      id: "4b755d38-7887-49fd-9a23-a6b8c45dc301"
    },
    movie: {
      id: "4b755d38-7887-49fd-9a23-a6b8c45dc301",
      price: 1488,
      name: "film"
    },
  };
}

const BASE_URL = "http://localhost:8080"
const HEADERS = {
  'Content-Type': 'application/json',
};


function createPayment() {
  const request = buildCreatePaymentRequest();

  const response = http.put(
      `${BASE_URL}/payments`,
      JSON.stringify(request),
      {headers: HEADERS}
  );

  check(response, {
    'status is 200': (r) => r.status === 200,
  })

  return response.json().id;
}

function getPayment(id) {
  const response = http.get(`${BASE_URL}/payments/${id}`);

  check(response, {
    'status is 200': (r) => r.status === 200,
  })
}

function proceedPayment(id) {
  const request = {
    paymentId: id,
    description: "desc"
  }

  const response = http.post(
      `${BASE_URL}/proceed/bombardier`,
      JSON.stringify(request),
      {headers: HEADERS}
  );

  check(response, {
    'status is 200': (r) => r.status === 200,
  })
}

export default function() {
  const paymentId = createPayment();
  getPayment(paymentId);
  proceedPayment(paymentId);
}
