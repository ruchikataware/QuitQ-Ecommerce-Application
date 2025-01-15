import axios from "axios";

const BASE_URL = "http://localhost:8080/api/customer/";

class CustomerService {
  addToCart(customerId, productId, quantity, accessToken) {
    return axios({
      method: 'post',
      url: BASE_URL + 'cart/' + customerId + '/' + productId + '/' + quantity + '/add',  
      headers: {
        'Access-Control-Allow-Origin': '*',
        'Authorization': `Bearer ${accessToken}`
      },
      withCredentials: true,
    });
  }

  getCart(id, token) {
    return axios({
      method: 'get',
      url: BASE_URL + 'cart/' + id,
      responseType: 'json',
      headers: {
        'Access-Control-Allow-Origin': '*',
        'Authorization': `Bearer ${token}`
      }
    })
  }

  getOrders(id, token) {
    return axios({
      method: 'get',
      url: BASE_URL + 'orders/' + id,
      responseType: 'json',
      headers: {
        'Access-Control-Allow-Origin': '*',
        'Authorization': `Bearer ${token}`
      }
    })
  }

  placeOrder(customerId, address, accessToken) {
    return axios({
      method: 'post',
      url: BASE_URL + 'orders/place-order/' + customerId,  
      data: address,       
      headers: {
        'Access-Control-Allow-Origin': '*',
        'Authorization': `Bearer ${accessToken}`
      },
      withCredentials: true,
    });
  }

  updateCart(id, productId, quantity, token) {
    return axios({
      method: 'put',
      url: BASE_URL + 'cart/' + id + '/' + productId + '/' + quantity + '/update',
      headers: {
        'Access-Control-Allow-Origin': '*',
        'Authorization': `Bearer ${token}`
      }
    });
  }

  deleteProductFromCart(id, productId, token) {
    return axios({
      method: 'delete',
      url: BASE_URL + 'cart/' + id + '/' + productId + '/remove',
      responseType: 'json',
      headers: {
        'Access-Control-Allow-Origin': '*',
        'Authorization': `Bearer ${token}`
      }
    })
  }
}

export default new CustomerService();