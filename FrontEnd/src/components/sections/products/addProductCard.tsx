import React, { useState, ChangeEvent, FormEvent } from "react";
import { Input } from "@/components/ui/input";
import { Textarea } from "@/components/ui/textarea";
import { Button } from "@/components/ui/button";
import api from "@/context/appContext";

export default function AddProductCard() {
  const [title, setTitle] = useState<string>("");
  const [details, setDetails] = useState<string>("");
  const [price, setPrice] = useState<string>("");
  const [quantity, setQuantity] = useState<string>("");
  const [imageFile, setImageFile] = useState<File | null>(null);
  const [isLoading, setIsLoading] = useState(false);

  // Cloudinary config
  const CLOUD_NAME = "dl8khwjss";
  const UPLOAD_PRESET = "LuxeLine";

  const handleImageChange = (e: ChangeEvent<HTMLInputElement>) => {
    if (e.target.files && e.target.files[0]) {
      setImageFile(e.target.files[0]);
    }
  };

  const uploadImageToCloudinary = async (): Promise<string | null> => {
    if (!imageFile) return null;

    const formData = new FormData();
    formData.append("file", imageFile);
    formData.append("upload_preset", UPLOAD_PRESET);

    try {
      const res = await fetch(`https://api.cloudinary.com/v1_1/${CLOUD_NAME}/image/upload`, {
        method: "POST",
        body: formData,
      });

      const data = await res.json();
      console.log("Cloudinary upload response:", data);
      return data.secure_url;
    } catch (err) {
      console.error("Cloudinary upload failed", err);
      return null;
    }
  };

  const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setIsLoading(true);

    const token = localStorage.getItem("token");

    try {
      const imageUrl = await uploadImageToCloudinary();

      if (!imageUrl) {
        alert("Image upload failed");
        setIsLoading(false);
        return;
      }

      const productData = {
        title,
        details,
        price: parseFloat(price),
        quantity: parseInt(quantity, 10),
        img: imageUrl,
      };

      const res = await api.post("http://localhost:8080/api/v1/product/create", productData, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      if (res.status === 201) {
        alert("Product added successfully");
        setTitle("");
        setDetails("");
        setPrice("");
        setQuantity("");
        setImageFile(null);
      } else {
        alert("Error: Something went wrong");
      }
    } catch (err: any) {
      console.error("Submit error", err);
      alert(err?.response?.data?.error || "Failed to add product");
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="max-w-xl mx-auto p-6 bg-white rounded-md shadow-md space-y-6">
      <h2 className="text-2xl font-semibold text-gray-700">Add Product</h2>
      <form onSubmit={handleSubmit} className="space-y-4">
        <div>
          <label htmlFor="title" className="block mb-1 font-medium text-gray-700">
            Title
          </label>
          <Input
            id="title"
            type="text"
            placeholder="Product title"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            required
          />
        </div>

        <div>
          <label htmlFor="details" className="block mb-1 font-medium text-gray-700">
            Details
          </label>
          <Textarea
            id="details"
            placeholder="Product details"
            value={details}
            onChange={(e: React.ChangeEvent<HTMLTextAreaElement>) => setDetails(e.target.value)}
            rows={4}
            required
          />
        </div>

        <div>
          <label htmlFor="price" className="block mb-1 font-medium text-gray-700">
            Price
          </label>
          <Input
            id="price"
            type="number"
            placeholder="Price"
            value={price}
            onChange={(e) => setPrice(e.target.value)}
            min="0"
            step="0.01"
            required
          />
        </div>

        <div>
          <label htmlFor="quantity" className="block mb-1 font-medium text-gray-700">
            Quantity
          </label>
          <Input
            id="quantity"
            type="number"
            placeholder="Quantity available"
            value={quantity}
            onChange={(e) => setQuantity(e.target.value)}
            min="0"
            required
          />
        </div>

        <div>
          <label htmlFor="image" className="block mb-1 font-medium text-gray-700">
            Image
          </label>
          <input
            id="image"
            type="file"
            accept="image/*"
            onChange={handleImageChange}
            className="block w-full text-sm text-gray-500 file:mr-4 file:py-2 file:px-4
                       file:rounded file:border-0 file:text-sm file:font-semibold
                       file:bg-blue-50 file:text-blue-700 hover:file:bg-blue-100"
          />
          {imageFile && (
            <p className="mt-2 text-sm text-gray-600">
              Selected: {imageFile.name}
            </p>
          )}
        </div>

        <Button type="submit" className="w-full" disabled={isLoading}>
          {isLoading ? "Adding..." : "Add Product"}
        </Button>
      </form>
    </div>
  );
}
