import { BrowserRouter, Routes, Route } from "react-router-dom";
import HomePage from "@/components/pages/home";
import SignInPage from "@/components/pages/SignIn";
import Product from "@/components/pages/product";
import { AuthProvider } from "./context/authContext";

const App = () => {
  return (
    <BrowserRouter>
        <AuthProvider>
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/signIn" element={<SignInPage />} />
        <Route path="/product" element={<Product />} />
      </Routes>
      </AuthProvider>
    </BrowserRouter>
  );
};

export default App;
