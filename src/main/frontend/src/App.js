import React, {useState} from 'react';
import './App.css';
import axios from 'axios';
import LoadingButton from '@mui/material/Button';
import Select from '@mui/material/Select';
import MenuItem from '@mui/material/MenuItem';
import OutlinedInput from '@mui/material/OutlinedInput';
import InputLabel from '@mui/material/InputLabel';
import {DataGrid} from '@mui/x-data-grid';
import TextField from '@mui/material/TextField';
import countryList from '../src/countryList/countries'

const EartquakeRecords = () => {
  
  const [loadingButton, setLoadingButton] = useState(false);
  const [dayCount, setDayCount] = useState(1);
  const [selectedCountry, setSelectedCountry] = useState("United States");
  const names = countryList;
  const [data, setData] = useState([]);


  const rows = data;
  const columns = [
    { field: 'country', headerName: 'Country', width: 150 },
    { field: 'place', headerName: 'Place', width: 350 },
    { field: 'magnitude', headerName: 'Magnitude', width: 150 },
    { field: 'date', headerName: 'Date', width: 350 },
  ];

  function getData() {
    setLoadingButton(true);
    
    const formData = new FormData();
    formData.append("dayCount",dayCount);
    formData.append("countryName", Array.isArray(selectedCountry) ? selectedCountry[0]:"United States" );

    axios.post(`http://localhost:8080/api/v1/earthquake-record/${selectedCountry[0]}/records`,
              formData,
              {
                headers: {
                  "Content-Type":"multipart/form-data"
                }
              }
    ).then((result) => {
      let gridData = [];

      if(result.data.length !== 0){
        result.data.map((data,index )=> {
          data["id"] = index;
  
          gridData = [...gridData, data]

        })
      } else {
        gridData = [{
          country:"",
          place:"",
          magnitude:"",
          date:`No Data Found in Past ${dayCount} days`,
          id:99
        }]
      }
      setData(gridData);
      setLoadingButton(false);

    }).catch(err => {

      console.log("fail")
      setLoadingButton(false);

    }) 
  }

  const ITEM_HEIGHT = 48;
  const ITEM_PADDING_TOP = 8;
  const MenuProps = {
    PaperProps: {
      style: {
        maxHeight: ITEM_HEIGHT * 4.5 + ITEM_PADDING_TOP,
        width: 250,
      },
    },
  };

  const handleChange = (event) => {
    const {
      target: { value },
    } = event;
    console.log(event)
    setSelectedCountry(
      typeof value === 'string' ? value.split(',') : value,
    );
  };

  const handleDayChange = (event) => {
    console.log(event.target.value)
    setDayCount(event.target.value)
  }
  const handleCountryChange = (event) => {
    setSelectedCountry(event.target.value)
  }

  return (
    <div>
      <h1 className='headerClass'>Earthquake Records</h1>
      <div>
        <span className='mt20'>
          <InputLabel id="demo-multiple-name-label" sx={{marginTop:"20px", color:"rgba(11, 11, 11, 0.7)"}}>Country Name</InputLabel>
          <Select
            labelId="demo-multiple-name-label"
            id="demo-multiple-name"
            value={selectedCountry}
            onChange={handleChange}
            input={<OutlinedInput label="Name" />}
            MenuProps={MenuProps}
          >
            {names.map((name, index) => (
              <MenuItem
                key={name}
                value={name}
              >
                {name}
              </MenuItem>
            ))}
          </Select>
        </span>
        <span className={'mt20'}>
          <InputLabel id="demo-multiple-name-label" sx={{marginTop:"20px", color:"rgba(11, 11, 11, 0.7)"}}>Record Day Interval</InputLabel>
          <TextField
            hiddenLabel
            id="filled-hidden-label-small"
            size="small"
            type={"number"}
            defaultValue="1"
            InputProps={{ inputProps: { min: 1, max: 30 } }}
            value={dayCount}
            onChange={handleDayChange}
          />
        </span>
      </div>

      <LoadingButton
          size="small"
          onClick={getData}
          variant="outlined"
          sx={{marginTop:"20px", color:"rgba(11, 11, 11, 0.7)", borderColor:"rgba(11, 11, 11, 0.3)"}}
          className={loadingButton ? "disable mt20":'mt20'}
        >
          Fetch data
        </LoadingButton>

        <div className='height centerGrid'>
          <DataGrid
            sx={{
              width:1100
            }}
            className='minHeight datagridProp'
            rows={rows}
            columns={columns}
            componentsProps={{
              columnMenu: { background: 'red', counter: rows.length },
            }}
          />
        </div>

    </div>
  )
}

function App() {
  return (
    <div className="App">
      <EartquakeRecords />
    </div>
  );
}

export default App;
