import { BrowserRouter, Routes, Route } from "react-router-dom";
import HomePage from "@/components/pages/home";
import SignInPage from "@/components/pages/SignIn";
import { AuthProvider } from "./context/authContext";
import ProductCard from "./components/sections/products/product";

const App = () => {
  return (
    <BrowserRouter>
        <AuthProvider>
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/signIn" element={<SignInPage />} />
        <Route path="/product/:id" element={<ProductCard />} />
      </Routes>
      </AuthProvider>
    </BrowserRouter>
  );
};

export default App;
