import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { StarIcon } from "@heroicons/react/24/solid";
import { HeartIcon } from "@heroicons/react/24/outline";
import Navbar from "@/components/sections/home/navbar";

interface Product {
  id: string;
  title: string;
  details: string;
  price: number;
  img: string;
  qty: number;
}

const ProductCard = () => {
  const { id } = useParams();
  const [product, setProduct] = useState<Product | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [quantity, setQuantity] = useState(1);
  const sizes = ["7", "8", "9", "10", "11", "12"];
  const [selectedSize, setSelectedSize] = useState("9");

  useEffect(() => {
    const fetchProduct = async () => {
      try {
        const res = await fetch(`http://localhost:8080/api/v1/product/${id}`);
        if (!res.ok) throw new Error("Failed to fetch product");

        const data = await res.json();
        setProduct(data);
      } catch (err: any) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchProduct();
  }, [id]);

  if (loading) return <div className="text-center mt-10">Loading...</div>;
  if (error || !product)
    return (
      <div className="text-center text-red-600 mt-10">
        {error || "Product not found"}
      </div>
    );

  return (
    <div className="bg-gray-50 min-h-screen py-12 px-4 sm:px-6 lg:px-8">
      <div className="pb-14 -mt-5">
        <Navbar />
      </div>

      <div className="max-w-7xl mx-auto">
        <div className="flex flex-col md:flex-row gap-8">
          {/* Product Image */}
          <div className="md:w-1/2">
            <div className="bg-white rounded-lg shadow-md overflow-hidden">
              <div className="h-96 bg-white flex items-center justify-center">
                <img
                  src={product.img}
                  alt={product.title}
                  className="h-full object-contain"
                />
              </div>
            </div>
          </div>

          {/* Product Info */}
          <div className="md:w-1/2">
            <div className="bg-white rounded-lg shadow-md p-6">
              <div className="flex justify-between items-start">
                <div>
                  <h1 className="text-2xl font-bold text-gray-900">
                    {product.title}
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
                <p className="text-3xl font-semibold text-gray-900">
                  RS. {product.price.toFixed(2)}
                </p>
                <p className="text-green-600 mt-1">
                  {product.qty > 0
                    ? "In stock and ready to ship"
                    : "Out of stock"}
                </p>
              </div>

              {/*Size */}
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

              {/* Quantity */}
              <div className="mt-6">
                <h2 className="text-sm font-medium text-gray-900">Quantity</h2>
                <div className="flex items-center mt-2">
                  <button
                    onClick={() => setQuantity(Math.max(1, quantity - 1))}
                    className="p-2 border border-gray-300 rounded-l-md"
                    disabled={quantity <= 1}
                  >
                    -
                  </button>
                  <div className="px-4 py-2 border-t border-b border-gray-300">
                    {quantity}
                  </div>
                  <button
                    onClick={() => {
                      if (quantity < product.qty) {
                        setQuantity(quantity + 1);
                      } else {
                        alert(
                          `Maximum available quantity is ${product.qty}`
                        );
                      }
                    }}
                    className={`p-2 border border-gray-300 rounded-r-md ${
                      quantity >= product.qty
                        ? "cursor-not-allowed opacity-50"
                        : ""
                    }`}
                    disabled={quantity >= product.qty}
                  >
                    +
                  </button>
                </div>
                {product.qty <= 0 && (
                  <p className="text-red-600 text-sm mt-1">
                    This product is currently out of stock.
                  </p>
                )}
              </div>

              {/* Buttons */}
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
                <p className="mt-2 text-gray-600">{product.details}</p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ProductCard;
