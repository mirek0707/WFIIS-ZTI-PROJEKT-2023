import { useState, useEffect, useReducer } from 'react'
import axios from 'axios'
import UserService from "../services/user_service";
import "bootstrap/dist/css/bootstrap.min.css";
import Form from "react-bootstrap/Form";
import Table from 'react-bootstrap/Table';
import FloatingLabel from 'react-bootstrap/FloatingLabel';
import Pagination from 'react-bootstrap/Pagination';
import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';
import Button from 'react-bootstrap/Button';


import moment from 'moment';
import countries from "i18n-iso-countries";
import CurrencyFormat from 'react-currency-format';
import ReactCountryFlag from "react-country-flag";
import plLocale from "i18n-iso-countries/langs/pl.json";


import CommentComp from './comment_component';

export default function ClubsComponent() {

    require("moment/min/locales.min");
    moment.locale('pl')
    const [content, setContent] = useState()
    const [selectedSeason, setSelectedSeason] = useState("")
    const [transferArr, setTransferArr] = useState([])
    const [playersArr, setPlayersArr] = useState([])
    
    const [status, setStatus] = useState("")

    const selectSeasonHandler = (value) => setSelectedSeason(value)
    const ButtonHandler = (value) => { setStatus(value) }

    countries.registerLocale(plLocale);

    const countryObj = countries.getNames("pl", { select: "official" });

    const countryArr = Object.entries(countryObj).map(([key, value]) => {
        return {
            label: value,
            value: key
        };
    });

    const reducer = (state, action) => {
        switch (action.type) {
            case "setPageCount":
                return { ...state, pageCount: action.payload };
            case "setCurrentData":
                return { ...state, currentData: action.payload };
            case "setOffset":
                return { ...state, offset: action.payload };
            case "setActivePage":
                return { ...state, activePage: action.payload };
            case "updateData":
                return { ...state, data: action.payload };
            default:
                throw new Error();
        }
    };

   const initialState = {
        data: transferArr,
        offset: 0,
        numberPerPage: 20,
        pageCount: 0,
        currentData: [],
        activePage: 1
    };

    const [state, dispatch] = useReducer(reducer, initialState);

    useEffect(() => {
        dispatch({
            type: "setCurrentData",
            payload: state.data.slice(
                state.offset,
                state.offset + state.numberPerPage
            )
        });
    }, [state.numberPerPage, state.offset, state.data]);

    function playerName(playerId) {
        const player = playersArr.find(obj => {
            return obj.id === playerId;
        });
        return player.known_as === "" ? player.name + " " + player.surname : player.known_as;
    }

    const handleClick = (e) => {
        const clickValue = parseInt(e.target.getAttribute("data-page"), 10);
        dispatch({
            type: "setOffset",
            payload: (clickValue - 1) * state.numberPerPage
        });
        dispatch({
            type: "setActivePage",
            payload: clickValue
        });
        dispatch({
            type: "setPageCount",
            payload: Math.ceil(state.data.length / state.numberPerPage)
        });
    };

    const paginationItems = [];
    const amountPages = Math.ceil(state.data.length / state.numberPerPage);
    for (let number = 1; number <= amountPages; number++) {
        if (amountPages > 1) {
            paginationItems.push(
            <Pagination.Item
                key={number}
                active={number === state.activePage}
                data-page={number}
            >
                {number}
            </Pagination.Item>
        );
        }
    }

    function countryCode(id) {
        const player = playersArr.find(obj => {
            return obj.id === id;
        });
        const codeByNation = countryArr.find(obj => {
            return obj.label === player.nationality;
        });
        return codeByNation.value;
    }

    function componentDidMount() {
        UserService.getUserBoard().then(
            response => {
                setContent(response.data.message);
            },
            error => {
                setContent(
                    (error.response &&
                        error.response.data &&
                        error.response.data.message) ||
                    error.message ||
                    error.toString()
                );
            }
        );
    }

    const getTransfersData = async () => {
        const dataJson = JSON.stringify({
            season: selectedSeason === "all" ? "" : selectedSeason
        })
        try {
            const res = await axios.post((process.env.baseURL || "http://localhost:8080") + '/api/test/getTransfers', dataJson, {
                headers: { 'Content-Type': 'application/json' }
            })
            if (res.status === 200) {
                setTransferArr(res.data)
                dispatch({
                    type: "updateData",
                    payload: res.data
                });
                dispatch({
                    type: "setOffset",
                    payload: 0
                });
                dispatch({
                    type: "setActivePage",
                    payload: 1
                });
                dispatch({
                    type: "setPageCount",
                    payload: 0
                });
            }
        }
        catch (err) {
        }
    }

    const getPlayers = async () => {
        const dataJson = JSON.stringify({
            nationality: "",
            position: "",
        })
        try {
            const res = await axios.post((process.env.baseURL || "http://localhost:8080") + '/api/test/getPlayers', dataJson, {
                headers: { 'Content-Type': 'application/json' }
            })
            if (res.status === 200) {
                setPlayersArr(res.data)
            }
        }
        catch (err) {
        }
    }

    

    useEffect(() => {
        componentDidMount()
        getPlayers()
        getTransfersData()
    }, [selectedSeason]) // eslint-disable-line react-hooks/exhaustive-deps

    useEffect(() => {
        window.scrollTo(0, 0)
    }, [status])

    return (
        <>
            {content === "Zawartość użytkownika." ? (
                <div className="container" style={{ padding: "3rem 0rem" }}>
                    {status === "" ? (
                        <>
                            <header className="jumbotron">
                                <h3>Transfery</h3>
                            </header>
                            <Col lg={3}>
                                <FloatingLabel controlId="floatingSelect" label="Sezon">
                                    <Form.Select size="lg"
                                        id="floatingSelect"
                                        value={selectedSeason}
                                        onChange={(e) => {
                                            selectSeasonHandler(e.target.value);
                                        }}
                                    >
                                        <option key={"all"} value={"all"}>Wszystkie</option>
                                        <option key={"22/23"} value={"22/23"}>22/23</option>
                                        <option key={"21/22"} value={"21/22"}>21/22</option>
                                        <option key={"20/21"} value={"20/21"}>20/21</option>
                                        <option key={"19/20"} value={"19/20"}>19/20</option>
                                        <option key={"18/19"} value={"18/19"}>18/19</option>
                                        <option key={"17/18"} value={"17/18"}>17/18</option>
                                        <option key={"16/17"} value={"16/17"}>16/17</option>
                                        <option key={"15/16"} value={"15/16"}>15/16</option>
                                        <option key={"14/15"} value={"14/15"}>14/15</option>
                                        <option key={"13/14"} value={"13/14"}>13/14</option>
                                        <option key={"12/13"} value={"12/13"}>12/13</option>
                                        <option key={"11/12"} value={"11/12"}>11/12</option>
                                        <option key={"10/11"} value={"10/11"}>10/11</option>
                                        <option key={"09/10"} value={"09/10"}>09/10</option>
                                        <option key={"08/09"} value={"08/09"}>08/09</option>
                                        <option key={"07/08"} value={"07/08"}>07/08</option>
                                        <option key={"06/07"} value={"06/07"}>06/07</option>
                                        <option key={"05/06"} value={"05/06"}>05/06</option>
                                    </Form.Select>
                                </FloatingLabel>
                            </Col>
                            <Table striped bordered hover size="sm">
                                <thead>
                                    <tr>
                                        <th>Zawodnik</th>
                                        <th>Z klubu</th>
                                        <th>Do klubu</th>
                                        <th>Typ</th>
                                        <th>Kwota</th>
                                        <th>Data</th>
                                        <th>Akcje</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {state.currentData &&
                                        state.currentData.map((item, index) => (
                                            <tr key={index}>
                                                <td>
                                                    <ReactCountryFlag
                                                        style={{
                                                            width: '1em',
                                                            height: '1em',
                                                        }}
                                                        countryCode={countryCode(item.player_id)}
                                                        svg
                                                        cdnUrl="https://cdnjs.cloudflare.com/ajax/libs/flag-icon-css/3.4.3/flags/1x1/"
                                                        cdnSuffix="svg"
                                                        title={countryCode(item.player_id)}
                                                    />
                                                    {" "}{playerName(item.player_id)}
                                                </td>
                                                <td>{item.club_left}</td>
                                                <td>{item.club_joined}</td>
                                                <td>{item.type}</td>
                                                <CurrencyFormat value={item.fee} displayType={'text'} thousandSeparator={true} suffix={' €'} renderText={value => <td>{value}</td>} />
                                                <td>{moment(item.date).format('DD.MM.YYYY')}</td>
                                                <td>
                                                    <Button variant="success" key={item._id} onClick={() => { ButtonHandler(item) }} >
                                                        Komentarze
                                                    </Button>
                                                </td>
                                            </tr>
                                        ))}
                                </tbody>
                            </Table>
                            <Pagination onClick={handleClick}>{paginationItems}</Pagination>
                        </>
                    ) : (
                        <>
                            <Row lg={2}>
                                <Col className="d-flex">
                                </Col>
                                <Col className="d-flex justify-content-end">
                                    <Button variant="success" onClick={() => { ButtonHandler("") }} >
                                        Wróć do wszystkich transferów
                                    </Button>
                                </Col>
                            </Row>
                            <p>
                                <strong>Zawodnik: </strong>
                                {playerName(status.player_id)}
                            </p>
                            <p>
                                <strong>Z klubu:</strong>{" "}
                                {status.club_left}
                            </p>
                            <p>
                                <strong>Do klubu:</strong>{" "}
                                {status.club_joined}
                            </p>
                            <p>
                                <strong>Typ transferu:</strong>{" "}
                                {status.type}
                            </p>
                            <p>
                                <strong>Kwota:</strong>{" "}
                                <CurrencyFormat value={status.fee} displayType={'text'} thousandSeparator={true} suffix={' €'} renderText={value => value} />
                            </p>
                            <p>
                                <strong>Data transferu:</strong>{" "}
                                {moment(status.date).format('DD.MM.YYYY')}
                            </p>

                            <CommentComp status = {status}/>
                            
                        </>
                    )}
                </div>
            ) : (
                <div className="container" style={{ padding: "3rem 0rem" }}>
                    <header className="jumbotron">
                        <h3>Brak dostępu do tej strony!</h3>
                    </header >
                </div>
            )}
        </>
    )

}
