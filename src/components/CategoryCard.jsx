import React from "react";

const CategoryCard = ({ category, onDelete, onUpdate }) => {
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
          <button style={{width: "30%", marginBottom: "10px"}} className="btn btn-primary me-2" onClick={() => onUpdate(category)}>
            Update
          </button>
          <button className="btn btn-danger" onClick={() => onDelete(category.categoryId)}>
            Delete
          </button>
        </div>
      </div>
    </div>
  );
};

export default CategoryCard;
