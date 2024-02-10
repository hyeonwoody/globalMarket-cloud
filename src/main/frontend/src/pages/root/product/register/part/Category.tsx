import React, {useState} from 'react';
import CategoryButton, {CategoryButtonProps} from "./CategoryButton";
import categoryButton from "./CategoryButton";



interface CategoryProps{
    category: Map<string, string[]>,
    callback: (result: string) => void
}
function Category(props:CategoryProps) {
    const [dropdown, setDropdown] = useState (false);
    const [buttonList, setButtonList] = useState<CategoryButtonProps[]>([]);

    const [currentLevel, setLevel] = useState<number>(0);

    const toggleDropdown = () => {
        setDropdown(!dropdown);
    }
    const deleteButton = (level: number) => {
        console.log("DELETE")
        setButtonList((prevButton) => {
            const savedButton = prevButton.filter(button => {
                return button.level < level;
            });
            return savedButton;
        });
    }

    const addNewButton = (result : string, level: number) => {
        console.log("ADD NEW 외부");
        // @ts-ignore
        if (props.category.get(result).length > 0) {
            console.log("ADD NEW")
            const categoryButtonProps: CategoryButtonProps = {
                category: props.category.get(result), // Access the category value from props
                callback: categorySelectionCallback, // Pass the callback function
                level: level+1 // Set the level value
            };
            setButtonList((prevButton) => {
                return [...prevButton, categoryButtonProps];
            })
        }
    };
    const handleButtonClick = (index : number) => {
        alert(`Button ${index + 1} clicked`);
    };

    const categorySelectionCallback = (result: string, level: number) => {
        console.log("BUtton CALLBACKk")
        console.log ("LEVEL : "+ level);
        console.log ("props category"+props.category.get(result));
        console.log ("ButtonList.length"+buttonList.length);
        if (level == buttonList.length && props.category.get(result)){
            console.log("ADDD NEW")
            addNewButton(result, level);
        }
        else if (level < buttonList.length){
            console.log ("EDIT button");
            deleteButton (level+1);
            addNewButton(result, level);
        }
        if (props.category.get(result) == undefined){
            props.callback (result);
        }
        console.log("FINAL")
        console.log(buttonList)
    }


    return (
        <div className={"button-Container -mx-3 relative"} id={"product-category"}>
            <div className="w-full md:w-full px-3 mb-6 md:mb-3">
                <label className="block text-gray-700 text-xs font-bold mb-2"
                       htmlFor="grid-product-url">
                    카테고리
                </label>
                <CategoryButton category={props.category.get("FIRST")} callback={categorySelectionCallback} level={0}/>
                {buttonList.map((button, index) =>(
                    <CategoryButton key={index} category={button.category} callback={categorySelectionCallback} level={button.level}/>
                ))}
            </div>
        </div>

    );
}

export default Category;