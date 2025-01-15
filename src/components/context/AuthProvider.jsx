import { createContext, useState, useEffect } from "react";
import { jwtDecode } from "jwt-decode";

const AuthContext = createContext({});

export const AuthProvider = ({ children }) => {
  const [auth, setAuth] = useState(
    () => {
      const storedAuth = localStorage.getItem("auth");
      if (storedAuth) {
        const parsedAuth = JSON.parse(storedAuth);
        return parsedAuth;
      }
      return null;
    }
  );

  const [isAdminLoggedIn, setIsAdminLoggedIn] = useState(auth?.role === "ADMIN");
  const [isCustomerLoggedIn, setIsCustomerLoggedIn] = useState(auth?.role === "CUSTOMER");
  const [isSellerLoggedIn, setIsSellerLoggedIn] = useState(auth?.role === "SELLER");

  useEffect(() => {
    if (auth) {
      localStorage.setItem("auth", JSON.stringify(auth));
      localStorage.setItem("isAdminLoggedIn", isAdminLoggedIn);
      localStorage.setItem("isCustomerLoggedIn", isCustomerLoggedIn);
      localStorage.setItem("isSellerLoggedIn", isSellerLoggedIn);
    } else {
      localStorage.removeItem("auth");
      localStorage.removeItem("isAdminLoggedIn");
      localStorage.removeItem("isCustomerLoggedIn");
      localStorage.removeItem("isSellerLoggedIn");
    }
  }, [auth, isAdminLoggedIn, isCustomerLoggedIn, isSellerLoggedIn]);

  const logout = () => {
    setAuth(null);
    setIsAdminLoggedIn(false);
    setIsCustomerLoggedIn(false);
    setIsSellerLoggedIn(false);
  };

  return (
    <AuthContext.Provider
      value={{
        auth,
        setAuth,
        logout,
        isAdminLoggedIn,
        setIsAdminLoggedIn,
        isCustomerLoggedIn,
        setIsCustomerLoggedIn,
        isSellerLoggedIn,
        setIsSellerLoggedIn,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
};

export default AuthContext;
