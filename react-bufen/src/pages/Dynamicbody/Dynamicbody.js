import axios from "axios";
import React, {useLayoutEffect, useState} from "react";
import Dynamic from "../../component/Dynamic/Dynamic";

const Dynamicbody = (props) => {

    const [dynamicList, setDynamicList] = useState([]);

    useLayoutEffect(() => {
        if (dynamicList.length < 1) {
            axios.post('/getDynamic', {
                tag: props.tag,
                authorId: props.authorId,
            })
                .then(function (response) {
                    console.log(response.data);
                    setDynamicList(response.data.data);
                })
                .catch(function (error) {
                    console.log(error);
                });
        }
    })

    return (
        <div>
            {
                dynamicList.filter((dynamic) => {
                    if (dynamic !== null)
                        return dynamic;
                }).map((dynamic, index) => {
                    return (
                        <Dynamic {...dynamic} {...dynamic.author} />
                    )
                })
            }
        </div>
    );
}

export default Dynamicbody;