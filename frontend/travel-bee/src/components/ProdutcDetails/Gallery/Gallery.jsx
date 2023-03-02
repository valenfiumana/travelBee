import './Gallery.css'
import React, { useState } from 'react';
import Lightbox from 'react-spring-lightbox';
import { Carousel } from "react-responsive-carousel";
import "react-responsive-carousel/lib/styles/carousel.min.css";

function Gallery({imagesList}) {
  const [isShown, setIsShown] = useState(false);
  const [currentImageIndex, setCurrentIndex] = useState(0);
  const images = imagesList.map(image => ({src: image.url, alt: image.title}));

  function renderStaticGallery() {
    const images2to5 =[]
    for (let i = 1; i < 5; i++) {
      imagesList[i] && images2to5.push(imagesList[i]);
    }
    return images2to5.map((image, i) => <img key={i} className={`img${i+2}`} src={image.url} alt={image.title} /> );
  }

  const handleClick = () => {
    setIsShown(true);;
  };

  const handleClose = () => {
    setIsShown(false);
  }

  const gotoPrevious = () => currentImageIndex > 0 && setCurrentIndex(currentImageIndex - 1);

  const gotoNext = () => {
    currentImageIndex + 1 < imagesList.length &&
    setCurrentIndex(currentImageIndex + 1);
  }

  return (
      <div className="Gallery">
        <div className='cont-iz'>
          <img className='img1' src={imagesList[0].url} alt="" />
        </div>
        <div className='cont-dr'>
          {renderStaticGallery()}
        </div>
        <Carousel className="carouselResponsive" showArrows={false} isShown={false} autoPlay={true} infiniteLoop={true} showStatus={false} interval={2500}>
          {imagesList.map((image, i) => (
            <div key={i}>
              <img src={image.url} alt={image.title} />
            </div>)
          )}
        </Carousel>
        <div className='btnContainer'>
          <button type="button" className="btn" onClick={handleClick}>
            Ver más fotos
          </button>
        </div>
        {isShown && (
            <Lightbox
            isOpen={true}
            onPrev={gotoPrevious}
            onNext={gotoNext}
            images={images}
            currentIndex={currentImageIndex}
            className="cool-class"
            onClose={handleClose}
            singleClickToZoom
            pageTransitionConfig={{
              from: { transform: "scale(0.75)", opacity: 0 },
              enter: { transform: "scale(1)", opacity: 1 },
              leave: { transform: "scale(0.75)", opacity: 0 },
              config: { mass: 1, tension: 320, friction: 32 }
            }}
          />
        )
        }
      </div>
  );
}
  
export default Gallery
