import axios from "axios";

const BASE_URL = "http://localhost:8080/api/admin/";

class AdminService {
  addCategory(category, accessToken) {
    return axios({
      method: 'post',
      url: BASE_URL + 'add-categories',  
      data: category,      
      headers: {
        'Access-Control-Allow-Origin': '*',
        'Authorization': `Bearer ${accessToken}`
      },
      withCredentials: true,
    });
  }

  updateCategory(id, category, token) {
    return axios({
      method: 'put',
      url: BASE_URL + 'update-category/' + id,  
      data: category,       
      headers: {
        'Access-Control-Allow-Origin': '*',
        'Authorization': `Bearer ${token}`
      }
    });
  }

  updateOrderStatus(id, status, token) {
    return axios({
      method: 'put',
      url: BASE_URL + 'update-order-status/' + id + '/' + status,  
      headers: {
        'Access-Control-Allow-Origin': '*',
        'Authorization': `Bearer ${token}`
      }
    });
  }

  deleteCategory(id, token) {
    return axios({
      method: 'delete',
      url: BASE_URL + 'delete-category/' + id,
      responseType: 'json',
      headers: {
        'Access-Control-Allow-Origin': '*',
        'Authorization': `Bearer ${token}`
      }
    })
  }

  getSellers(token) {
    return axios({
      method: 'get',
      url: BASE_URL + "get-all-sellers",
      responseType: 'json',
      headers: {
        'Access-Control-Allow-Origin': '*',
        'Authorization': `Bearer ${token}`
      }
    })
  }

  getOrders(token) {
    return axios({
      method: 'get',
      url: BASE_URL + "orders",
      responseType: 'json',
      headers: {
        'Access-Control-Allow-Origin': '*',
        'Authorization': `Bearer ${token}`
      }
    })
  }

  deleteSeller(id, token) {
    return axios({
      method: 'delete',
      url: BASE_URL + 'delete-seller/' + id,
      responseType: 'json',
      headers: {
        'Access-Control-Allow-Origin': '*',
        'Authorization': `Bearer ${token}`
      }
    })
  }

  getProducts(token) {
    return axios({
      method: 'get',
      url: BASE_URL + "view-products",
      responseType: 'json',
      headers: {
        'Access-Control-Allow-Origin': '*',
        'Authorization': `Bearer ${token}`
      }
    })
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

  getCustomers(token) {
    return axios({
      method: 'get',
      url: BASE_URL + "get-all-customers",
      responseType: 'json',
      headers: {
        'Access-Control-Allow-Origin': '*',
        'Authorization': `Bearer ${token}`
      }
    })
  }

  deleteCustomer(id, token) {
    return axios({
      method: 'delete',
      url: BASE_URL + 'delete-customer/' + id,
      responseType: 'json',
      headers: {
        'Access-Control-Allow-Origin': '*',
        'Authorization': `Bearer ${token}`
      }
    })
  }
}

export default new AdminService();
