import { useContext, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import AuthContext from "./context/AuthProvider";

const LogoutComponent = () => {
  console.log("LogoutComponent() is called..");
  const { logout } = useContext(AuthContext);
  const navigate = useNavigate();

  useEffect(() => {
    console.log("LogoutComponent useEffect() is called..");
    logout(); 
    navigate("/", { replace: true }); 
  }, [logout, navigate]);

  return <h2>Logging out...</h2>;  
};

export default LogoutComponent;
