import { PlusCircleFilled, CloseCircleFilled } from '@ant-design/icons';
import { useEffect } from 'react';
import { useContext, useState } from 'react';
import { PublicationContext } from '../../../context/PublicationContext';
import compareTitle from '../../../utils/comapreTitle';

function PublicationImages({productImages}){
  const { publicationDataValue, policiesUpdatedValue, imagesUpdatedValue } = useContext(PublicationContext);
  const [publicationData, setPublicationData] = publicationDataValue;
  const [policiesUpdated , setPoliciesUpdated] = policiesUpdatedValue;
  const [imagesUpdated , setImagesUpdated] = imagesUpdatedValue;
  const [imageInputValue, setImageInputValue] = useState([
    {title: '1', url: ''},
    {title: '2', url: ''},
    {title: '3', url: ''},
    {title: '4', url: ''},
    {title: '5', url: ''},
  ]);
  let timeout;

  useEffect(() => {
    if (productImages && policiesUpdated) {
      const actualImages = productImages.map(image => ({title: image.title, url: image.url}))
      setImageInputValue(actualImages);
      setPublicationData({...publicationData, images: actualImages});
    }
  }, [productImages, policiesUpdated]);

  useEffect(() => {
    publicationData.images.length && !imagesUpdated && setImagesUpdated(true);
  }, [publicationData]);

  function handleChange(e) {
    const title = e.target.dataset.title;
    const url = e.target.value;

    const images = [...(imageInputValue.filter(image => image['title'] !== title )), {title: title, url: url}]
    images.sort(compareTitle);
    setImageInputValue([...images]);

    //debounce state change
    clearTimeout(timeout);
    timeout = setTimeout(() => {
      // const images = [...(publicationData.images.filter(image => image[Object.keys(image)[0]] !== title )), {title: title, url: url}]
      // images.sort(compareTitle);
      setPublicationData({...publicationData, images: images});
    }, 300);
  }

  function addInput() {
    setImageInputValue([...imageInputValue, {title: `${imageInputValue.length + 1}` , url: ''}]);
  }

  function removeInput() {
    const images = [...publicationData.images]
    images.pop();
    setPublicationData({...publicationData, images: images});

    const inputList = [...imageInputValue];
    inputList.pop();
    setImageInputValue(inputList);
  }

  return (
    <div className="publication-images">
      <h4>Cargar imágenes</h4>
      <div className="input-container attributes images">
        <div className="input attribute-input">
          {imageInputValue.map((image, i) => 
            <div key={i} className="attribute-upload">
              <input onChange={handleChange} id="imageInput" value={image.url} data-title={image.title} type='text' placeholder='Ej: https://www.images.com/image2342.jpg, .jpeg, .png, .webp' required multiple accept=".png, .jpg, .jpeg, .webp" />
              {i === 4 && <PlusCircleFilled onClick={addInput} className='add-btn' style={{color: 'var(--yellow)', fontSize: '43px', marginLeft:'15px'}} />}
              {i !== 4 && i < imageInputValue.length -1  && <div style={{height: '43px', minWidth: '43px', margin:'0 3px 0 15px'}}></div>}
              {i === imageInputValue.length -1 && i !== 4 && <CloseCircleFilled onClick={() => removeInput(i)} className='remove-btn' style={{color: 'red', fontSize: '43px', marginLeft:'15px'}} />}
            </div>
          )}
        </div>
      </div>
    </div>
  );
}

export default PublicationImages
