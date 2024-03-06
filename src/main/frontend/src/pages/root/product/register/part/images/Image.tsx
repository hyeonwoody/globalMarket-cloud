import React, {useState} from 'react';
import {CallbackStrategy, ProductImage} from "../../ProductRegisterAPI";

interface ImageProps {
    fetchData : boolean,
    images : ProductImage,
    callback : (data : ProductImage) => void
}

function Image (props : ImageProps) {
    const [images, setImages] = useState <ProductImage> (props.images);
    const inputImageCache : ProductImage = props.images;

    if (props.fetchData) {
        props.callback(images);
    }

    const ImageFinal = (strategy : keyof CallbackStrategy, index? : number) => {
        switch (strategy){
            case "Create":
                break;
            case "Reset":
                setImages(inputImageCache as ProductImage);
                break;
            case "Delete":
                if (index as number === 0 && images?.optionalImages.length !== 0){
                    setImages((prevState: ProductImage) => {
                        images.representativeImage.url = prevState.optionalImages[0].url;
                        let updatedImages = prevState.optionalImages.filter((_, idx) =>idx !== 0);
                        return {
                            ...prevState,
                            ["optionalImages"]: updatedImages,
                        }
                    })
                }
                else if(index as number > 0)
                {
                    setImages( (prevState : ProductImage) => {
                        let updatedImages = prevState.optionalImages.filter((_, idx) => idx !== (index as number)-1);
                        return {
                            ...prevState,
                            ["optionalImages"]: updatedImages,
                        }
                    })
                }
                break;
            case "Switch":
                // const {representativeImage, optionalImages} = prevState;
                // const updatedOptionalImages = [...optionalImages];
                // if (index !== undefined && 0 <= index && index < optionalImages.length){
                //     const updatedRepresentativeImage = updatedOptionalImages[index];
                //     updatedOptionalImages[index] = representativeImage;
                //     return {
                //         ...prevState,
                //         representativeImage: updatedRepresentativeImage,
                //         optionalImages: updatedOptionalImages,
                //     };
                // }
                // return prevState;

                setImages( (prevState : ProductImage) => {
                    const updatedOptionalImages = [...prevState.optionalImages];
                    if (index !== undefined && 0 <= index && index < updatedOptionalImages.length){
                        const updatedRepresentativeImage = updatedOptionalImages[index];
                        updatedOptionalImages[index] = prevState.representativeImage;
                        return {
                            ...prevState,
                            representativeImage: updatedRepresentativeImage,
                            optionalImages: updatedOptionalImages,
                        };
                    }
                    return prevState;
                })
        }
    }

    const onClickDelete = (event : React.MouseEvent<HTMLButtonElement>, index : number) => {
        event.preventDefault();
        console.log("DE"+index)
        ImageFinal("Delete", index);
    }
    function onClickSelect(event: React.MouseEvent<HTMLButtonElement>, index: number) {
        event.preventDefault();
        if (index > 0){
            ImageFinal("Switch", index-1);
        }
    }
    const onClickReset = (event : React.MouseEvent<HTMLButtonElement>) => {
        event.preventDefault();
        ImageFinal("Reset");
    }
    const imgInnerStyles : React.CSSProperties = {
        display: "flex",
        justifyContent: "space-between"
    }

    const imgStyles: React.CSSProperties = {
        boxSizing: "border-box"
    }
    interface ButtonStyles {
        color: string;
        top: string;
        left: string;
        position: any; // Use the correct type for position
    }

    const SelectButtonStyles : (index : number) => ButtonStyles = (index) => {
        const columns = 3;
        const rowIndex = Math.floor(index / columns);
        const columnIndex = index % columns;
        const top = 505 + rowIndex * 400;
        const left = 620 + columnIndex * 400;

        return {
            color: "white",
            top: `${top}px`,
            left: `${left}px`,
            position: "absolute",
            display: "flex",
            opacity : "0.3"
        }
    };
    const DeleteButtonStyles : (index : number) => ButtonStyles = (index) => {
        const columns = 3;
        const rowIndex = Math.floor(index / columns);
        const columnIndex = index % columns;
        const top = 460 + rowIndex * 400;
        const left = 620 + columnIndex * 400;

        return {
            color: "white",
            top: `${top}px`,
            left: `${left}px`,
            position: "absolute",
            display: "flex",
            opacity : "0.3"
        }
    };
    return (
        <div className="Image-Container flex flex-wrap -mx-3 mb-2" id={"product-images"}>
                <div className="w-full md:w-full px-3 mb-0 md:mb-2" id={"product-representative"}>
                    <label className="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2"
                           htmlFor="grid-product-name">
                        대표 이미지
                    </label>
                    <button
                        className="bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 rounded-full mx-auto block"
                        onClick={onClickReset}>
                        다시
                    </button>
                    <div style={imgInnerStyles}>
                        <div className={"product-representa-images"}>
                            <div className="grid grid-cols-3 gap-4">
                                <div className={"image-Container representative-img"} style={imgInnerStyles}>
                                    <img key={"image0"} style={imgStyles} className={"max-w-sm max-h-sm"}
                                         src={images?.representativeImage.url}/>
                                    <button
                                        key={"DeleteButton0"}
                                        className="bg-green-500 hover:bg-green-700 font-bold w-10 h-10 rounded-full"
                                        style={DeleteButtonStyles(0)}

                                        onClick={(event) => onClickDelete(event, 0)}>
                                        <svg className="w-6 h-6 text-gray-800 dark:text-white ml-2 mt-2"
                                             aria-hidden="true"
                                             xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                                            <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                                                  stroke-width="2"
                                                  d="M5 7h14m-9 3v8m4-8v8M10 3h4a1 1 0 0 1 1 1v3H9V4a1 1 0 0 1 1-1ZM6 7h12v13a1 1 0 0 1-1 1H7a1 1 0 0 1-1-1V7Z"/>
                                        </svg>
                                    </button>

                                </div>
                                {images?.optionalImages.map((value, index) => (

                                    <div className={"image-Container optional-imgs"} style={imgInnerStyles} key={index}>
                                        <img key={"image" + index + 1} className={"max-w-sm max-h-sm"} src={value.url}/>
                                        <button
                                            key={"DeleteButton" + index + 1}
                                            className="bg-green-500 hover:bg-green-700 font-bold w-10 h-10 rounded-full"
                                            style={DeleteButtonStyles(index + 1)}
                                            onClick={(event) => onClickDelete(event, index + 1)}

                                        >
                                        <svg className="w-6 h-6 text-gray-800 dark:text-white ml-2 mt-2"
                                                 aria-hidden="true"
                                                 xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                                                <path stroke="currentColor" stroke-linecap="round"
                                                      stroke-linejoin="round"
                                                      stroke-width="2"
                                                      d="M5 7h14m-9 3v8m4-8v8M10 3h4a1 1 0 0 1 1 1v3H9V4a1 1 0 0 1 1-1ZM6 7h12v13a1 1 0 0 1-1 1H7a1 1 0 0 1-1-1V7Z"/>
                                            </svg>
                                        </button>
                                        <button
                                            key={"SelectButton" + index + 1}
                                            className="bg-red-500 hover:bg-red-700 font-bold w-10 h-10 rounded-full"
                                            style={SelectButtonStyles(index + 1)}
                                            onClick={(event) => onClickSelect(event, index + 1)}

                                        >

                                            <svg className="w-6 h-6 text-gray-800 dark:text-white ml-2 mt-2" aria-hidden="true"
                                                 xmlns="http://www.w3.org/2000/svg" fill="currentColor"
                                                 viewBox="0 0 24 24">
                                                <path fill-rule="evenodd"
                                                      d="M12 2a3 3 0 0 0-2.1.9l-.9.9a1 1 0 0 1-.7.3H7a3 3 0 0 0-3 3v1.2c0 .3 0 .5-.2.7l-1 .9a3 3 0 0 0 0 4.2l1 .9c.2.2.3.4.3.7V17a3 3 0 0 0 3 3h1.2c.3 0 .5 0 .7.2l.9 1a3 3 0 0 0 4.2 0l.9-1c.2-.2.4-.3.7-.3H17a3 3 0 0 0 3-3v-1.2c0-.3 0-.5.2-.7l1-.9a3 3 0 0 0 0-4.2l-1-.9a1 1 0 0 1-.3-.7V7a3 3 0 0 0-3-3h-1.2a1 1 0 0 1-.7-.2l-.9-1A3 3 0 0 0 12 2Zm3.7 7.7a1 1 0 1 0-1.4-1.4L10 12.6l-1.3-1.3a1 1 0 0 0-1.4 1.4l2 2c.4.4 1 .4 1.4 0l5-5Z"
                                                      clip-rule="evenodd"/>
                                            </svg>

                                        </button>
                                    </div>

                                ))}
                            </div>
                        </div>
                    </div>
                </div>
        </div>
    )

}

export default Image;
