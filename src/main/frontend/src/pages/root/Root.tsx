import React from 'react';
import RootAxios from "./RootAPI"
const Root: React.FC = () => {

    const onClick = (event : React.MouseEvent<HTMLButtonElement>) => {
        event.preventDefault();
        function resultCallback (result : number){
            console.log ("result"+result);

            if (result < 0){
                //error handling
            }
        }
        RootAxios (resultCallback);
    }


    return (
        <div>
            <p>fvcvxc</p>

            <button className="py-2 px-4 rounded-3xl shadow-md text-white bg-blue-500 hover:bg-green-300"
                    onClick={onClick}>submit
            </button>
        </div>
    )
}

export default Root;