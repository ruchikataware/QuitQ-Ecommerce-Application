import axios from "axios"

const BASE_URL = "http://localhost:8080/api/auth/";

class AuthService{
  registerCustomer(customer){
    return axios.post(BASE_URL + 'register-customer', customer);
  }

  loginCustomer(customer){
    return axios.post(BASE_URL + 'login-customer', customer);
  }

  registerSeller(seller){
    return axios.post(BASE_URL + 'register-seller', seller);
  }

  loginSeller(seller){
    return axios.post(BASE_URL + 'login-seller', seller);
  }

  loginAdmin(admin){
    return axios.post(BASE_URL + 'login-admin', admin)
  }        
}

export default new AuthService()


 

// class EmployeeService {
//     getAllEmployees(token) {
       
//         return axios({
//             method: 'get',
//             url: BASE_URL ,
//             responseType: 'json',
//             headers: {
//                 'Access-Control-Allow-Origin': '*',
//                 'Authorization': `Bearer ${token}`
//             }
 
//         })
//     }
//     createEmployee(employee,token){
//         return axios({
//             method: 'post',
//             url: BASE_URL,  // Use the base URL directly
//             data: employee,       // Include the object in the request
//             headers: {
//                 'Access-Control-Allow-Origin': '*',
//                 'Authorization': `Bearer ${token}`
//             }
//         });
//     }
 
//     getEmployeeByid(id,token){
//         return axios({
//             method: 'get',
//             url: BASE_URL + "/" + id,
//             responseType: 'json',
//             headers: {
//                 'Access-Control-Allow-Origin': '*',
//                 'Authorization': `Bearer ${token}`
//             }
 
//         })
//     }
 
//     updateEmployee(id,employee,token){
 
//         return axios({
//             method: 'put',
//             url: BASE_URL + '/' + id,  // Use the base URL directly
//             data: employee,       // Include the object in the request
//             headers: {
//                 'Access-Control-Allow-Origin': '*',
//                 'Authorization': `Bearer ${token}`
//             }
 
//         });
//     }
 
//     deleteEmployee(id,token){
//         return axios({
//             method: 'delete',
//             url: BASE_URL + "/" + id,
//             responseType: 'json',
//             headers: {
//                 'Access-Control-Allow-Origin': '*',
//                 'Authorization': `Bearer ${token}`
//             }
//         })
//     }
//     
//     loginUser(user){
//         return axios.post('http://localhost:8081/api/authenticate/login',user)
 
// }
