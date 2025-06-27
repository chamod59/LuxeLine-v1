import { useState } from "react";
import api from "@/context/appContext";
import signInImg from "@/assets/signIn-img/index1.png";
import signUpImg from "@/assets/signIn-img/index2.png";
import { useNavigate } from "react-router-dom";

const auth = () => {
  const navigate = useNavigate();
  const [isSignIn, setIsSignIn] = useState(true);
  const [userName, setUserName] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const [form, setForm] = useState({
    name: "",
    email: "",
    username: "",
    password: "",
  });

  const handleSignIn = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      const res = await api.post("/api/v1/auth/login", {
        username: userName,
        password,
      });

      const token = res.data.token;
      localStorage.setItem("token", token); 
      setError("");
      navigate("/"); 
    } catch (err: any) {
      const backendError =
        err?.response?.data?.error || err?.response?.data?.message;
      setError(backendError || "Login failed");
    }
  };

  const handleSignUp = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      await api.post("/api/v1/auth/register", {
        ...form,
      });

      setIsSignIn(true);
    } catch (err: any) {
      const backendError =
        err?.response?.data?.error || err?.response?.data?.message;

      setError(backendError || "Sign Up failed");
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-200 font-sans">
      <div className="flex bg-white rounded-lg overflow-hidden shadow-lg max-w-4xl w-full">
        {/* Left Panel */}
        <div className="w-full md:w-1/2 p-8 bg-teal-100">
          {isSignIn ? (
            <>
              <h2 className="text-2xl font-bold mb-6 text-center text-gray-800">
                Sign in
              </h2>
              <form onSubmit={handleSignIn} className="space-y-4">
                <div>
                  <input
                    value={userName}
                    onChange={(e) => setUserName(e.target.value)}
                    type="userName"
                    placeholder="Enter your UserName"
                    className="w-full px-4 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-teal-400"
                  />
                  <label className="block text-sm text-gray-700 mt-1">
                    User Name
                  </label>
                </div>

                <div>
                  <input
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    type="password"
                    placeholder="Enter Password"
                    className="w-full px-4 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-teal-400"
                  />
                  <label className="block text-sm text-gray-700 mt-1">
                    Password
                  </label>
                </div>
                {error && (
                  <p className="text-red-500 text-sm mt-2 text-center">
                    {error}
                  </p>
                )}

                <div className="flex justify-between items-center text-sm">
                  <label className="flex items-center gap-2">
                    <input type="checkbox" className="accent-teal-500" />
                    Remember Me
                  </label>
                  <a href="#" className="text-blue-600 hover:underline">
                    Forgotten Password?
                  </a>
                </div>

                <button
                  type="submit"
                  className="w-full bg-teal-600 text-white py-2 rounded hover:bg-teal-700 transition"
                >
                  Sign In
                </button>

                <div className="text-center text-sm mt-2">
                  New to eshop?{" "}
                  <button
                    type="button"
                    className="text-blue-600 hover:underline"
                    onClick={() => setIsSignIn(false)}
                  >
                    Join Now
                  </button>
                </div>

                <div className="text-center text-sm mt-1">
                  <a href="#" className="text-red-600 hover:underline">
                    Admin Login!
                  </a>
                </div>
              </form>
            </>
          ) : (
            <>
              <h2 className="text-2xl font-bold mb-6 text-center text-gray-800">
                Sign up now
              </h2>
              <form
                onSubmit={handleSignUp}
                className="grid grid-cols-1 md:grid-cols-2 gap-4"
              >
                <div className="col-span-2">
                  <input
                    value={form.name}
                    onChange={(e) => setForm({ ...form, name: e.target.value })}
                    type="fullName"
                    placeholder="Enter your Name"
                    className="w-full px-4 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-teal-400"
                  />
                  <label className="block text-sm text-gray-700 mt-1">
                    Full Name
                  </label>
                </div>

                <div className="col-span-2">
                  <input
                    value={form.email}
                    onChange={(e) =>
                      setForm({ ...form, email: e.target.value })
                    }
                    type="email"
                    placeholder="Enter your Email"
                    className="w-full px-4 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-teal-400"
                  />
                  <label className="block text-sm text-gray-700 mt-1">
                    Email
                  </label>
                </div>

                <div className="col-span-2">
                  <input
                    value={form.username}
                    onChange={(e) =>
                      setForm({ ...form, username: e.target.value })
                    }
                    type="username"
                    placeholder="Enter your Username"
                    className="w-full px-4 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-teal-400"
                  />
                  <label className="block text-sm text-gray-700 mt-1">
                    User Name
                  </label>
                </div>

                <div className="col-span-2">
                  <input
                    value={form.password}
                    onChange={(e) =>
                      setForm({ ...form, password: e.target.value })
                    }
                    type="password"
                    placeholder="Enter your Password"
                    className="w-full px-4 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-teal-400"
                  />
                  <label className="block text-sm text-gray-700 mt-1">
                    Password
                  </label>
                </div>
                {error && (
                  <p className="text-red-500 text-sm mt-2 text-center">
                    {error}
                  </p>
                )}

                <div className="col-span-2 mt-2">
                  <button
                    type="submit"
                    className="w-full bg-teal-600 text-white py-2 rounded hover:bg-teal-700 transition"
                  >
                    Sign Up
                  </button>
                </div>

                <div className="col-span-2 text-center text-sm">
                  Already have an Account?{" "}
                  <button
                    type="button"
                    className="text-blue-600 hover:underline"
                    onClick={() => setIsSignIn(true)}
                  >
                    Sign In
                  </button>
                </div>

                <div className="col-span-2 flex justify-center gap-4 mt-2">
                  <a href="#">
                    <img
                      src="https://img.icons8.com/color/24/google-logo.png"
                      alt="Google"
                    />
                  </a>
                  <a href="#">
                    <img
                      src="https://img.icons8.com/ios-filled/24/facebook-new.png"
                      alt="Facebook"
                    />
                  </a>
                  <a href="#">
                    <img
                      src="https://img.icons8.com/ios-filled/24/twitter.png"
                      alt="Twitter"
                    />
                  </a>
                </div>
              </form>
            </>
          )}

          <p className="text-xs text-center text-gray-500 mt-6">
            ©2023 LuxeLine.com | ALL Rights Reserved
          </p>
        </div>

        {/* Right Image */}
        <div className="hidden md:block md:w-1/2">
          <img
            src={isSignIn ? signInImg : signUpImg}
            alt="Visual"
            className="w-full h-full object-cover"
          />
        </div>
      </div>
    </div>
  );
};

export default auth;
