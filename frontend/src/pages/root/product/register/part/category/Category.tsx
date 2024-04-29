import React, {useEffect, useState} from 'react';
import CategoryButton, {CategoryButtonProps} from "./CategoryButton";

interface CategoryProps{
    category: Map<string, string[]>,
    callback: (result: string[]) => void
}

interface CategoryButton {
    category : string[] | undefined
}

function Category(props:CategoryProps) {
    const categorySelectionCallback = (result: string, level: number) => {
        console.log("BUtton CALLBACKk")
        console.log ("LEVEL : "+ level);
        console.log ("props category"+props.category.get(result));
        console.log ("ButtonList.length"+buttonList.length);
        if (level == buttonList.length && props.category.get(result)){
            addNewButton(result);
        }
        else if (level < buttonList.length){
            console.log ("EDIT button");
            deleteButton (level);
            addNewButton(result);
        }
        CategoryFinal (result, level);
    }

    const [buttonList, setButtonList] = useState<CategoryButton[]>([
    ]);
    const [category, setCategory] = useState<string[]>([]);

    useEffect(() => {
        console.log(buttonList.length);
        console.log(props.category.get("FIRST"));
            setButtonList([{category: props.category.get("FIRST")}]);
    }, [props.category]);
    const CategoryFinal = (result : string, level : number) => {
        if (level < category.length){
            setCategory( (prevState) => {
                const updatedCategory = prevState.filter((button, index) =>{
                    return index < level;
                });
                updatedCategory[level-1] = result;
                console.log("FF" + updatedCategory);
                return updatedCategory;
            })
        }
        else {
            setCategory((prevState) => {
                return [...prevState, result];
            });
        }
        if (props.category.get(result) == undefined){ //leaf category
            setCategory((prevState) => {
                props.callback(prevState);
                return prevState;
            });
        }
    }

    const deleteButton = (level: number) => {
        console.log("DELETE")
        setButtonList((prevButton) => {
            const savedButton = prevButton.filter((button, index) => {
                return index < level;
            });
            return savedButton;
        });
    }

    const addNewButton = (result : string) => {
        // @ts-ignore
        if (props.category.get(result).length > 0) {
            const updatedCategoryButton: CategoryButton = {
                ["category"]: props.category.get(result), // Access the category value from props
            };
            setButtonList((prevButton) => {
                return [...prevButton, updatedCategoryButton];
            })
        }
    };



    return (
        <div className={"button-Container -mx-3 relative"} id={"product-category"}>
            <div className="w-full md:w-full px-3 mb-6 md:mb-3">
                <label className="block text-gray-700 text-xs font-bold mb-2"
                       htmlFor="grid-product-url">
                    카테고리
                </label>
                <p className="text-xs">(생활건강,수집품,코스튬플레이,소품)</p>
                <p className="text-xs">(패션잡화,남성신발,부츠,롱부츠)</p>
                <p className="text-xs">(패션잡화,남성신발,슬립온)</p>
                <div className={"grid grid-cols-5"}>
                    {buttonList.map((button, index) => (
                        <CategoryButton key={index} category={button.category as string[]}
                                        callback={categorySelectionCallback} level={index + 1}/>
                    ))}
                </div>

            </div>
        </div>

    );
}

export default Category;