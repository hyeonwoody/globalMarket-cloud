import React from 'react';
import {usePage} from "../../../store/PageContext";
function BreadCrumb() {

    const {pageState, clear, move, back, forward} =usePage();
    return (
        <div className="Container">

            <div className="border-gray-200 bg-green-400 dark:bg-gray-800 dark:border-gray-700 flex flex-wrap items-center justify-between mx-auto">
                <a className="text-white">{pageState.page.join(" > ")}</a>
            </div>
        </div>

    );
}

export default BreadCrumb;