import React from 'react';
import { Link } from 'react-router-dom';

const HomePage: React.FC = () => {
  return (
    <div className="flex flex-col items-center justify-center text-center min-h-[calc(100vh-200px)]">
      <h1 className="text-5xl md:text-6xl font-extrabold text-gray-900 leading-tight">
        
        Welcome to <span className="text-blue-600">ArogyaTejas</span>
      </h1>
      <p className="mt-4 max-w-2xl text-lg text-gray-600">
        Your health, simplified. Seamlessly book and manage your medical appointments with the best specialists in town.
      </p>
      <div className="mt-8 flex flex-wrap justify-center gap-4">
        <Link
          to="/login"
          className="px-8 py-3 text-lg font-semibold text-white bg-blue-600 rounded-lg shadow-md hover:bg-blue-700 transition duration-300"
        >
          Get Started
        </Link>
        <Link
          to="/register"
          className="px-8 py-3 text-lg font-semibold text-blue-600 bg-white border border-blue-600 rounded-lg shadow-md hover:bg-blue-50 transition duration-300"
        >
          Create Account
        </Link>
      </div>
    </div>
  );
};

export default HomePage;
