import { useState } from "react";
import { StarIcon } from "@heroicons/react/24/solid";
import { HeartIcon } from "@heroicons/react/24/outline";
import Navbar from "@/components/sections/home/navbar";

const ProductCard = () => {
  const [selectedColor, setSelectedColor] = useState("black");
  const [selectedSize, setSelectedSize] = useState("9");
  const [quantity, setQuantity] = useState(1);

  const colors = ["black", "white", "red"];
  const sizes = ["7", "8", "9", "10", "11", "12"];

  return (
    <div className="bg-gray-50 min-h-screen py-12 px-4 sm:px-6 lg:px-8">
      <div className="pb-14 -mt-5">
        <Navbar />
      </div>

      <div className="max-w-7xl mx-auto">
        <div className="flex flex-col md:flex-row gap-8">
          {/* Product Images */}
          <div className="md:w-1/2">
            <div className="bg-white rounded-lg shadow-md overflow-hidden">
              <div className="h-96 bg-gray-200 flex items-center justify-center">
                <span className="text-gray-500 text-lg">Product Image</span>
              </div>
              <div className="p-4 flex gap-2">
                {[1, 2, 3, 4].map((item) => (
                  <div
                    key={item}
                    className="w-20 h-20 bg-gray-100 rounded cursor-pointer"
                  ></div>
                ))}
              </div>
            </div>
          </div>

          {/* Product Info */}
          <div className="md:w-1/2">
            <div className="bg-white rounded-lg shadow-md p-6">
              <div className="flex justify-between items-start">
                <div>
                  <h1 className="text-2xl font-bold text-gray-900">
                    BBS MID MEN'S BASKETBALL SHOES
                  </h1>
                  <div className="flex items-center mt-2">
                    <div className="flex">
                      {[0, 1, 2, 3, 4].map((rating) => (
                        <StarIcon
                          key={rating}
                          className={`h-5 w-5 ${
                            rating < 4 ? "text-yellow-400" : "text-gray-300"
                          }`}
                        />
                      ))}
                    </div>
                    <span className="text-gray-500 ml-2">(24 reviews)</span>
                  </div>
                </div>
                <button className="p-2 rounded-full hover:bg-gray-100">
                  <HeartIcon className="h-6 w-6 text-gray-400" />
                </button>
              </div>

              <div className="mt-6">
                <p className="text-3xl font-semibold text-gray-900">$74.89</p>
                <p className="text-green-600 mt-1">
                  In stock and ready to ship
                </p>
              </div>

              <div className="mt-8">
                <h2 className="text-sm font-medium text-gray-900">Color</h2>
                <div className="flex gap-2 mt-2">
                  {colors.map((color) => (
                    <button
                      key={color}
                      onClick={() => setSelectedColor(color)}
                      className={`w-8 h-8 rounded-full border-2 ${
                        selectedColor === color
                          ? "border-gray-900"
                          : "border-transparent"
                      } ${
                        color === "black"
                          ? "bg-black"
                          : color === "white"
                          ? "bg-white border-gray-300"
                          : "bg-red-500"
                      }`}
                    ></button>
                  ))}
                </div>
              </div>

              <div className="mt-6">
                <h2 className="text-sm font-medium text-gray-900">Size</h2>
                <div className="grid grid-cols-3 gap-2 mt-2">
                  {sizes.map((size) => (
                    <button
                      key={size}
                      onClick={() => setSelectedSize(size)}
                      className={`py-2 px-4 rounded-md border ${
                        selectedSize === size
                          ? "bg-gray-900 text-white border-gray-900"
                          : "bg-white text-gray-900 border-gray-300"
                      }`}
                    >
                      {size}
                    </button>
                  ))}
                </div>
              </div>

              <div className="mt-6">
                <h2 className="text-sm font-medium text-gray-900">Quantity</h2>
                <div className="flex items-center mt-2">
                  <button
                    onClick={() => setQuantity(Math.max(1, quantity - 1))}
                    className="p-2 border border-gray-300 rounded-l-md"
                  >
                    -
                  </button>
                  <div className="px-4 py-2 border-t border-b border-gray-300">
                    {quantity}
                  </div>
                  <button
                    onClick={() => setQuantity(quantity + 1)}
                    className="p-2 border border-gray-300 rounded-r-md"
                  >
                    +
                  </button>
                </div>
              </div>

              <div className="mt-8 flex gap-4">
                <button className="flex-1 bg-gray-900 text-white py-3 px-8 rounded-md font-medium hover:bg-gray-700">
                  Add to cart
                </button>
                <button className="flex-1 bg-indigo-600 text-white py-3 px-8 rounded-md font-medium hover:bg-indigo-700">
                  Buy now
                </button>
              </div>

              <div className="mt-8 border-t border-gray-200 pt-6">
                <h2 className="text-sm font-medium text-gray-900">
                  Description
                </h2>
                <p className="mt-2 text-gray-600">
                  High-performance basketball shoes designed for optimal court
                  grip and ankle support. Features breathable mesh upper,
                  cushioned midsole, and durable rubber outsole.
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ProductCard;
