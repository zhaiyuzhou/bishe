import React, { useState } from 'react';
import { Input, Image, Upload, Button } from 'antd'; import './PubDyn.css'
import { PlusOutlined, PictureOutlined } from '@ant-design/icons';
const { TextArea } = Input;

const getBase64 = (file) =>
  new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => resolve(reader.result);
    reader.onerror = (error) => reject(error);
  });

const Pubdyn = () => {

  // 上传的问字
  const [value, setValue] = useState('');

  // 改变视图
  const [isimgload,setIsimgload] = useState(false);

  // 上传的图片
  const [previewOpen, setPreviewOpen] = useState(false);
  const [previewImage, setPreviewImage] = useState('');
  const [fileList, setFileList] = useState([]);
  const handlePreview = async (file) => {
    if (!file.url && !file.preview) {
      file.preview = await getBase64(file.originFileObj);
    }
    setPreviewImage(file.url || file.preview);
    setPreviewOpen(true);
  };
  const handleChange = ({ fileList: newFileList }) => setFileList(newFileList);
  const uploadButton = (
    <button
      style={{
        border: 0,
        background: 'none',
      }}
      type="button"
    >
      <PlusOutlined />
      <div
        style={{
          marginTop: 8,
        }}
      >
        上传
      </div>
    </button>
  );

  console.log(fileList)
  return (
    <div className='Pubdyn-div'>
      <TextArea
        value={value}
        onChange={(e) => setValue(e.target.value)}
        placeholder="Controlled autosize"
        autoSize={{
          minRows: 3,
          maxRows: 5,
        }}
      />
      <Button type="text" icon={<PictureOutlined />}/>
      <div className='Pubdyn-upload-img'>
        <Upload
          action="http://localhost:3000/"
          listType="picture-card"
          fileList={fileList}
          onPreview={handlePreview}
          onChange={handleChange}
        >
          {fileList.length >= 8 ? null : uploadButton}
        </Upload>
        {previewImage && (
          <Image
            wrapperStyle={{
              display: 'none',
            }}
            preview={{
              visible: previewOpen,
              onVisibleChange: (visible) => setPreviewOpen(visible),
              afterOpenChange: (visible) => !visible && setPreviewImage(''),
            }}
            src={previewImage}
          />
        )}
      </div>
    </div>
  )};

export default Pubdyn;