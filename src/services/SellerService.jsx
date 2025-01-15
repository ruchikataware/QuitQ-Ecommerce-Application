import axios from "axios";

const BASE_URL = "http://localhost:8080/api/seller/";

class SellerService {
  addProduct(product, accessToken) {
    return axios({
      method: 'post',
      url: BASE_URL + 'add-product',  
      data: product,       
      headers: {
        'Access-Control-Allow-Origin': '*',
        'Authorization': `Bearer ${accessToken}`
      },
      withCredentials: true,
    });
  }

  getProducts(id, token) {
    return axios({
      method: 'get',
      url: BASE_URL + id + '/show-products',
      responseType: 'json',
      headers: {
        'Access-Control-Allow-Origin': '*',
        'Authorization': `Bearer ${token}`
      }
    })
  }

  getOrderItems(id, token) {
    return axios({
      method: 'get',
      url: BASE_URL + 'show-order-items/' + id,
      responseType: 'json',
      headers: {
        'Access-Control-Allow-Origin': '*',
        'Authorization': `Bearer ${token}`
      }
    })
  }

  getProductsByCategory(id, cid, token) {
    return axios({
      method: 'get',
      url: BASE_URL + id + '/' + cid + '/products-by-category',
      responseType: 'json',
      headers: {
        'Access-Control-Allow-Origin': '*',
        'Authorization': `Bearer ${token}`
      }
    })
  }

  updateProduct(id, product, token) {
    return axios({
      method: 'put',
      url: BASE_URL + 'update-product/' + id,  
      data: product,       
      headers: {
        'Access-Control-Allow-Origin': '*',
        'Authorization': `Bearer ${token}`
      }
    });
  }

  deleteProduct(id, token) {
    return axios({
      method: 'delete',
      url: BASE_URL + 'delete-product/' + id,
      responseType: 'json',
      headers: {
        'Access-Control-Allow-Origin': '*',
        'Authorization': `Bearer ${token}`
      }
    })
  }
}

export default new SellerService();