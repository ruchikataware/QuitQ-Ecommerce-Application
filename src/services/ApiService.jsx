import axios from "axios";

const BASE_URL = "http://localhost:8080/api/";
class ApiService {
  showCategories() {
    return axios.get("http://localhost:8080/api/show-categories");
  }

  getProductsByCategory(id) {
    return axios.get(BASE_URL + id + '/products-by-category');
  }

  getAllProducts() {
    return axios.get(BASE_URL + 'view-products');
  }
}

export default new ApiService();