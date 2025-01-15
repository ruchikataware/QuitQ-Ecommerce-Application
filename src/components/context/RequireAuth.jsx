import React, { useContext } from "react";
import { Navigate, Outlet, useLocation } from "react-router-dom";
import AuthContext from "./AuthProvider";

const RequireAuth = ({ allowedRoles }) => {
  const { auth } = useContext(AuthContext);
  const location = useLocation();

  return (
    auth?.accessToken && auth?.role && allowedRoles.includes(auth.role)
      ? <Outlet />
      : <Navigate to="/" state={{ from: location }} replace />
  );
};

export default RequireAuth;
