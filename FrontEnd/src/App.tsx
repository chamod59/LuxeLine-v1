import { BrowserRouter, Routes, Route } from "react-router-dom";
import HomePage from "@/components/pages/home";
import SignInPage from "@/components/pages/SignIn";

const App = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/signIn" element={<SignInPage />} />
      </Routes>
    </BrowserRouter>
  );
};

export default App;
