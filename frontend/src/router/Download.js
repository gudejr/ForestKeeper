import axios from "axios";
import { useEffect, useState } from "react";
import shortlogo from "../res/img/logo_temp.png";
import landing from "../res/img/landing-removebg.png";
import mt_detail from "../res/img/mt_detail.jpg";
import ranking_list from "../res/img/ranking_list.jpg";
import matching_list from "../res/img/matching_list.jpg";
import matching_detail from "../res/img/matching_detail.jpg";
import mypage from "../res/img/mypage.jpg";
import plogging_result from "../res/img/plogging_result.jpg";

function Download() {
  const [userNum, setUserNum] = useState(0);
  const [dist, setDist] = useState(0);
  const welcome = () => {
    axios.get("https://k6a306.p.ssafy.io/api/plogging/welcome").then((res) => {
      setUserNum(res.data.numberOfUsers);
      setDist(res.data.totalDistance);
    });
  };
  const mobileVersion = () => {
    document.location.href = "https://k6a306.p.ssafy.io/m";
  };
  useEffect(() => {
    welcome();
    if (window.outerWidth < 800 || window.innerWidth < 800) {
      mobileVersion();
    }
  }, []);
  return (
    <div style={{ fontFamily: "'Noto Sans KR', sans-serif" }}>
      <div style={{ position: "sticky", top: "0", height: "10vh", backgroundColor: "white" }}>
        <div style={{ margin: "auto 15vw", height: "10vh", display: "flex", justifyContent: "space-between" }}>
          <a style={{ margin: "auto 0", fontWeight: "700", textDecoration: "none", color: "black" }} href={"https://k6a306.p.ssafy.io"}>
            <div style={{ margin: "auto 0", display: "flex", justifyContent: "space-around" }}>
              <img src={shortlogo} alt="" style={{ height: "7vh" }} />
              <div style={{ margin: "auto 1vw", fontSize: "3vh", fontWeight: "700" }}>Forest Keeper</div>
            </div>
          </a>
          <div style={{ margin: "auto 0", display: "flex", justifyContent: "space-around", width: "15vw" }}>
            <a style={{ fontWeight: "700", textDecoration: "none", color: "black" }} href={"https://k6a306.p.ssafy.io/api/apk"}>
              DOWNLOAD
            </a>
            <a style={{ fontWeight: "700", textDecoration: "none", color: "black" }} href={"https://lab.ssafy.com/s06-final/S06P31A306"}>
              DOCS
            </a>
          </div>
        </div>
      </div>
      <div style={{ padding: "2vh 12vw", height: "50vh", backgroundColor: "#B8DEB3", display: "flex" }}>
        <div style={{ margin: "7vh 5vh", width: "50vw" }}>
          <div style={{ display: "flex", flexDirection: "column", alignItems: "center" }}>
            <div style={{ margin: "0", fontSize: "5vh", fontWeight: "700", color: "black" }}>????????? ???????????? ??????</div>
            <div style={{ margin: "0", fontSize: "5vh", fontWeight: "700", color: "black" }}>Forest Keeper</div>
            <button
              style={{
                backgroundColor: "#37CD8D",
                border: "none",
                borderRadius: "2rem",
                width: "15vw",
                height: "6vh",
                color: "white",
                margin: "7vh 0",
              }}
              onClick={() => (document.location.href = `/`)}
            >
              <a style={{ margin: "auto 0", fontWeight: "700", textDecoration: "none", color: "white" }} href={"https://k6a306.p.ssafy.io/api/apk"}>
                <div style={{ margin: "0", fontSize: "3vh", fontWeight: "700", color: "white" }}>????????????</div>
              </a>
            </button>
            <div style={{ marginTop: "0vh" }}>
              <span style={{ margin: "0", fontSize: "2.5vh", fontWeight: "500", color: "black" }}>?????? </span>
              <span style={{ margin: "0", fontSize: "3vh", fontWeight: "700", color: "black" }}>{userNum}</span>
              <span style={{ margin: "0", fontSize: "2.5vh", fontWeight: "500", color: "black" }}>?????? </span>
              <span style={{ margin: "0", fontSize: "3vh", fontWeight: "700", color: "black" }}>{dist}</span>
              <span style={{ margin: "0", fontSize: "2.5vh", fontWeight: "500", color: "black" }}>km??? ????????? </span>
              <span style={{ margin: "0", fontSize: "2.5vh", fontWeight: "500", color: "black" }}>????????? ????????? ????????????!</span>
            </div>
          </div>
        </div>
        <img src={landing} alt="" style={{ float: "right" }} />
      </div>
      <div style={{ height: "55vh", backgroundColor: "white", display: "flex" }}>
        <div style={{ margin: "0 10vw", padding: "5vh 0 0 10vw", width: "35vw" }}>
          <img src={mt_detail} alt="" style={{ width: "10vw", boxShadow: "0px 5px 10px 2px darkgray", borderRadius: "10px" }} />
          <img src={ranking_list} alt="" style={{ width: "10vw", boxShadow: "0px 5px 10px 2px darkgray", borderRadius: "10px", margin: "-8vh" }} />
        </div>
        <div style={{ marginRight: "20vw", padding: "15vh 0", width: "45vw" }}>
          <div style={{ fontSize: "3.5vh", fontWeight: "700" }}>?????? ????????? ????????????</div>
          <div style={{ fontSize: "3.5vh", fontWeight: "700" }}>????????? ???????????? ??????????????????.</div>
          <div style={{ fontSize: "2vh", marginTop: "3vh" }}>????????? ???????????? ?????? ????????? ?????? ??? ??????.</div>
          <div style={{ fontSize: "2vh" }}>????????? ???????????? ?????? ???????????? ?????? ?????? ????????? ??? ?????????.</div>
          <div style={{ fontSize: "2vh" }}>????????? ????????????, ??????????????? ????????? ??? ????????????.</div>
        </div>
      </div>
      <div style={{ height: "55vh", backgroundColor: "#EAEAEA", display: "flex" }}>
        <div style={{ margin: "0 10vw", padding: "15vh 0 0 10vw", width: "45vw" }}>
          <div style={{ fontSize: "3.5vh", fontWeight: "700" }}>?????? ???????????? ????????? ???????????????.</div>
          <div style={{ fontSize: "2vh", marginTop: "3vh" }}>?????? ?????? ?????? ?????????????????? ?????? ???????????????????</div>
          <div style={{ fontSize: "2vh" }}>?????? ???????????? ????????? ????????? ??? ????????????.</div>
          <div style={{ fontSize: "2vh" }}>????????? ??????, ????????? ????????? ?????? ??????!</div>
        </div>
        <div style={{ marginRight: "20vw", padding: "5vh 0", width: "30vw" }}>
          <img src={matching_list} alt="" style={{ width: "10vw", boxShadow: "0px 5px 10px 2px darkgray", borderRadius: "10px" }} />
          <img src={matching_detail} alt="" style={{ width: "10vw", boxShadow: "0px 5px 10px 2px darkgray", borderRadius: "10px", margin: "-8vh" }} />
        </div>
      </div>
      <div style={{ height: "55vh", backgroundColor: "white", display: "flex" }}>
        <div style={{ margin: "0 10vw", padding: "5vh 0 0 10vw", width: "35vw" }}>
          <img src={mypage} alt="" style={{ width: "10vw", boxShadow: "0px 5px 10px 2px darkgray", borderRadius: "10px" }} />
          <img src={plogging_result} alt="" style={{ width: "10vw", boxShadow: "0px 5px 10px 2px darkgray", borderRadius: "10px", margin: "-8vh" }} />
        </div>
        <div style={{ marginRight: "20vw", padding: "15vh 0", width: "45vw" }}>
          <div style={{ fontSize: "3.5vh", fontWeight: "700" }}>????????? ???????????? ????????? ???????????????.</div>
          <div style={{ fontSize: "2vh", marginTop: "3vh" }}>?????? ???????????? ????????? ?????? ??????!</div>
          <div style={{ fontSize: "2vh" }}>?????? ???????????? ?????? ????????? ?????? ???????????? ??????????????????.</div>
          <div style={{ fontSize: "2vh" }}>????????????????????? ?????? ???????????? ????????? ?????????????????????~</div>
        </div>
      </div>
      <div style={{ height: "18vh", backgroundColor: "#B8DEB3" }}>
        <div style={{ margin: "0 15vw", paddingTop: "1vh", height: "2vh", display: "flex", justifyContent: "space-between" }}>
          <div style={{ display: "flex", justifyContent: "space-around" }}>
            <img src={shortlogo} alt="" style={{ height: "7vh" }} />
            <div style={{ margin: "1vh 1vh", fontSize: "3vh", fontWeight: "700" }}>Forest Keeper</div>
          </div>
          <div style={{ marginTop: "12vh" }}>?? 2022 ForestKeeper All rights reserved.</div>
          <div style={{ margin: "1vh 0", display: "flex", justifyContent: "space-around", width: "15vw" }}>
            <a style={{ fontWeight: "700", textDecoration: "none", color: "black" }} href={"https://k6a306.p.ssafy.io/api/apk"}>
              DOWNLOAD
            </a>
            <a style={{ fontWeight: "700", textDecoration: "none", color: "black" }} href={"https://lab.ssafy.com/s06-final/S06P31A306"}>
              DOCS
            </a>
          </div>
        </div>
      </div>
    </div>
  );
}
export default Download;
