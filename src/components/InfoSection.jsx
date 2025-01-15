import './InfoSection.css';
import ViewCategoryCard from './ViewCategoryCard';
import ViewProductsByCategorySeller from './ViewProductsByCategorySeller';
import ApiService from '../services/ApiService';
import React, { useEffect, useState } from 'react';
import ViewProductsByCategory from './ViewProductsByCategory';

const InfoSection = () => {
    console.log("InfoSection() is called..");
    const [categories, setCategories] = useState([]);
    const [activeComponent, setActiveComponent] = useState("view");
    const [selectedCategoryId, setSelectedCategoryId] = useState(null);
    useEffect(() => {
        console.log("InfoSection useEffect() is called..");
        ApiService.showCategories().then((response) => {
            setCategories(response.data);
        });
    }, []);

    const handleViewProducts = (categoryId) => {
        console.log("InfoSection handleViewProducts() is called..");
        setSelectedCategoryId(categoryId);
        setActiveComponent("");
    };
    return activeComponent === "view" ? (
        <section id="info" className="info-section">
            <h2>Find Everything You Need at QuitQ</h2>
            <div className="info-content">
                <div className="info-text">
                    <p>
                        Our online platform offers an extensive collection of high-quality products,
                        ensuring that you’ll find exactly what you’re looking for.
                    </p>
                </div>
                <div className="row" style={{padding: "40px"}}>
                    {categories.map((category) => (
                        <ViewCategoryCard
                            key={category.categoryId}
                            category={category}
                            onViewProducts={handleViewProducts}
                        />
                    ))}
                </div>
            </div>
        </section>
    ) : (
        <ViewProductsByCategory
            categoryId={selectedCategoryId}
            onBack={() => setActiveComponent("view")}
        />
    );
};

export default InfoSection;
