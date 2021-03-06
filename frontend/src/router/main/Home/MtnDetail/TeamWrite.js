import React, { useRef, useEffect, forwardRef, useState } from "react";
import Bar from "../Bar";
import { useParams } from "react-router-dom";
import Send from "../../../../config/Send";
import "../Home.css";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";

function TeamWrite() {
  const [title, setTitle] = useState();
  const [content, setContent] = useState();
  let useParam = useParams();
  const [resultDate, setResultDate] = useState();
  const [date, setDate] = useState(new Date());
  const [total, setTotal] = useState(2);
  const ExampleCustomInput = forwardRef(({ value, onClick }, ref) => (
    <button
      className="example-custom-input"
      onClick={onClick}
      ref={ref}
      style={{
        width: "40vw",
        height: "4vh",
        borderRadius: 4,
        border: "none",
        background: "white",
        border: "1px solid #C4C4C4",
        fontSize: "2vh",
      }}
    >
      {value}
    </button>
  ));

  function GetMonth(date) {
    var month = date.getMonth() + 1;
    return month < 10 ? "0" + month : "" + month;
  }
  function GetDate(date) {
    var date = date.getDate();
    return date < 10 ? "0" + date : "" + date;
  }
  useEffect(() => {
    setResultDate(
      date.getFullYear() + "-" + GetMonth(date) + "-" + GetDate(date)
    );
  }, [date]);

  useEffect(() => {
    setResultDate(
      date.getFullYear() + "-" + GetMonth(date) + "-" + GetDate(date)
    );
  }, []);

  const onTitleHandler = (event) => {
    setTitle(event.currentTarget.value);
  };
  const onContentHandler = (event) => {
    setContent(event.currentTarget.value);
  };
  const onTotalHandler = (event) => {
    setTotal(event.currentTarget.value);
  };

  function register() {
    const mountainCode = useParam.mountainCode;
    const data = {
      mountainCode: mountainCode,
      title: title,
      content: content,
      ploggingDate: resultDate,
      total: total,
    };
    console.log(data);
    Send.post(`/match`, data)
      .then((res) => {
        console.log(res);
        if (res.data.statusCode === 201) {
          alert("?????? ?????????????????????.");
          document.location.href = `/teamList/${useParam.mountainCode}`;
        }
      })
      .catch((e) => {
        console.log(e);
      });
  }

  return (
    <>
      <div
        style={{
          height: "84.5vh",
          width: "81.8vw",
          marginLeft: "9.1vw",
        }}
      >
        <div id="date" style={{ marginBottom: "5vh", marginTop: "8VH" }}>
          <div
            style={{ color: "#8ABC9A", marginTop: "2vh", marginBottom: "3vh" }}
          >
            ????????? ??????????????????
          </div>
          <div>
            <DatePicker
              selected={date}
              onChange={(date) => setDate(date)}
              customInput={<ExampleCustomInput />}
            />
          </div>
        </div>
        <div id="date" style={{ marginBottom: "6vh" }}>
          <div
            style={{ color: "#8ABC9A", marginTop: "2vh", marginBottom: "3vh" }}
          >
            ????????? ?????????????????? (?????? 20???)
          </div>
          <div>
            <select
              className="w150"
              onChange={onTotalHandler}
              value={total}
              style={{
                width: "40vw",
                height: "4vh",
                textAlign: "center",
                fontSize: "2vh",
              }}
            >
              <option value="2">2???</option>
              <option value="3">3???</option>
              <option value="4">4???</option>
              <option value="5">5???</option>
              <option value="6">6???</option>
              <option value="7">7???</option>
              <option value="8">8???</option>
              <option value="9">9???</option>
              <option value="10">19???</option>
              <option value="11">11???</option>
              <option value="12">12???</option>
              <option value="13">13???</option>
              <option value="14">14???</option>
              <option value="15">15???</option>
              <option value="16">16???</option>
              <option value="17">17???</option>
              <option value="18">18???</option>
              <option value="19">19???</option>
              <option value="20">20???</option>
            </select>
          </div>
        </div>

        <div id="title" style={{ marginBottom: "5vh" }}>
          <div
            style={{ color: "#8ABC9A", marginTop: "2vh", marginBottom: "2vh" }}
          >
            ????????? ??????????????????
          </div>
          <textarea
            class="textarea"
            placeholder="????????? ??????????????????."
            onChange={onTitleHandler}
            style={{
              height: "3.5vh",
              // paddingBottom: "1vh",
              width: "80vw",
              fontSize: "2.1vh",
            }}
          ></textarea>
        </div>

        <div
          style={{ color: "#8ABC9A", marginTop: "2vh", marginBottom: "2vh" }}
        >
          ????????? ??????????????????
        </div>
        <textarea
          class="textarea"
          rows="1"
          maxlength="5000"
          placeholder="????????? ???????????????."
          onChange={onContentHandler}
          style={{
            marginTop: "2vh",
            overflow: "auto",
            width: "80vw",
            fontSize: "2.1vh",
            height: "20vh",
          }}
        ></textarea>

        <button
          onClick={register}
          style={{
            background: "#37CD8D",
            color: "white",
            border: "none",
            borderRadius: 5,
            marginLeft: "33vw",
            marginTop: "5vh",
            width: "14vw",
            height: "4vh",
            fontSize: "1.7vh",
          }}
        >
          ??????
        </button>
      </div>
      <Bar></Bar>
    </>
  );
}

export default TeamWrite;
