import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import './SearchBar.css';
import { useSearch } from "./context/SearchContext";

const SearchBar = () => {
  console.log("SearchBar() is called..");
  const { setSearchQuery } = useSearch();
  const [query, setQuery] = useState("");
  const navigate = useNavigate();

  const handleSearch = (e) => {
    console.log("handleSearch() is called..");
    e.preventDefault();
    if (query.trim()) {
      setSearchQuery(query.trim());
      navigate(`/view-products/${query.trim()}`);
    }
  };

  return (
    <form className="search-bar" onSubmit={handleSearch}>
      <input
        type="text"
        className="search-input"
        placeholder="Search products..."
        value={query}
        onChange={(e) => setQuery(e.target.value)}
      />
      <button type="submit" className="search-button">
        <i className="bi bi-search"></i> 
      </button>
    </form>
  );
};

export default SearchBar;
