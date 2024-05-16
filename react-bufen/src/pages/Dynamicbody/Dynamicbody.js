import axios from "axios";
import React, {useEffect, useState, useMemo} from "react";
import Dynamic from "../../component/Dynamic/Dynamic";

const Dynamicbody = (props) => {

    const [dynamicList, setDynamicList] = useState([]);
    const [ifa, setIfa] = useState([]);

    // 删除动态
    const delDynamicforList = (deldynamic) => {
        setDynamicList(dynamicList.filter(dynamic => dynamic.id !== deldynamic.id));
    }

    useEffect(() => {
        axios.post('/api/getDynamic', {
            tag: props.tag,
            authorId: props.authorId,
            time: props.times,
            searchDate: props.searchDate,
            like: props.like,
        })
            .then(function (response) {
                console.log(response.data);
                setDynamicList(response.data.data);
                if (typeof props.newDynamic !== "undefined" && props.newDynamic !== dynamicList.at(0))
                    setDynamicList(prevDynamicList => [props.newDynamic, ...prevDynamicList]);

                // 查看各位作者是否已被用户关注
                axios.post("/api/ifAttentions", {
                    userIds: ([...response.data.data.map((dynamic, index) => {
                        return dynamic.author.id;
                    })]),
                }).then((res) => {
                    setIfa(res.data.data);
                })
            })
            .catch(function (error) {
                console.log(error);
            });


    }, [props.tag, props.authorId, props.times, props.searchDate, props.like, props.isLogin])

    return (
        <div>
            {
                useMemo(() => {
                    return (
                        dynamicList != null ?
                            (dynamicList.filter((dynamic) => dynamic !== null).map((dynamic, index) => {
                                return (
                                    <Dynamic key={index} {...dynamic} isLogin={props.isLogin} del={props.del}
                                             delDynamicforList={delDynamicforList} setTransmit={props.setTransmit}
                                             setOther={props.setOther} ifa={ifa === null ? false : ifa[index]}/>
                                )
                            })) : (<></>)
                    )
                })
            }
        </div>
    );
}

export default Dynamicbody;