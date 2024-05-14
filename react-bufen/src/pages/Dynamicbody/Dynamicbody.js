import axios from "axios";
import React, {useLayoutEffect, useState} from "react";
import Dynamic from "../../component/Dynamic/Dynamic";

const Dynamicbody = (props) => {

    const [dynamicList, setDynamicList] = useState([]);

    // 删除动态
    const delDynamicforList = (deldynamic) => {
        setDynamicList(dynamicList.filter(dynamic => dynamic.id !== deldynamic.id));
    }

    useLayoutEffect(() => {
        axios.post('/api/getDynamic', {
            tag: props.tag,
            authorId: props.authorId,
            time: props.times,
            searchDate: props.searchDate,
        })
            .then(function (response) {
                console.log(response.data);
                setDynamicList(response.data.data);
                if (typeof props.newDynamic !== "undefined" && props.newDynamic !== dynamicList.at(0))
                    setDynamicList([props.newDynamic, ...dynamicList]);
            })
            .catch(function (error) {
                console.log(error);
            });
    }, [props.isLogin, props.newDynamic, props.times, props.tag, props.authorId])

    return (
        <div>
            {
                dynamicList != null ?
                    (dynamicList.filter((dynamic) => dynamic !== null).map((dynamic, index) => {
                        return (
                            <Dynamic key={index} {...dynamic} isLogin={props.isLogin} del={props.del}
                                     delDynamicforList={delDynamicforList} setTransmit={props.setTransmit}/>
                        )
                    })) : (<></>)
            }
        </div>
    );
}

export default Dynamicbody;