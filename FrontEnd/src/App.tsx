import { BrowserRouter, Routes, Route } from "react-router-dom";
import HomePage from "@/components/pages/home";
import SignIn from "@/components/pages/SignIn";

const App = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/SignIn" element={<SignIn />} />
      </Routes>
    </BrowserRouter>
  );
};

export default App;
