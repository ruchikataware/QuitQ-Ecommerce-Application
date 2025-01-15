import React from "react";

const ViewCategoryCard = ({ category, onViewProducts }) => {
  return (
    <div className="col-md-4 mb-4">
      <div className="card">
        <img
          src={category.image}
          className="card-img-top"
          alt={category.name}
          style={{ height: "200px", objectFit: "cover" }}
        />
        <div className="card-body">
          <h5 className="card-title">{category.name}</h5>
          <p className="card-text">{category.description || "No description available"}</p>
          <button
            className="btn btn-primary"
            onClick={() => onViewProducts(category.categoryId)}
          >
            View Products
          </button>
        </div>
      </div>
    </div>
  );
};

export default ViewCategoryCard;
