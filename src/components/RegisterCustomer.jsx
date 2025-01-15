import React, { useEffect, useRef, useState } from 'react';
import './register.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCheck, faInfoCircle, faTimes } from '@fortawesome/free-solid-svg-icons';
import { Link, useNavigate } from 'react-router-dom';
import Header from './Header';
import AuthService from '../services/AuthService';

const USER_REGEX = /^[a-zA-Z][a-zA-Z0-9-_]{3,23}$/;
const PWD_REGEX = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%]).{8,24}$/;
const PHONE_REGEX = /^[0-9]{10}$/;
const EMAIL_REGEX = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

export const RegisterCustomerComponent = () => {
  console.log("RegisterCustomerComponent() is called..");
  const userRef = useRef();
  const errorRef = useRef();
  const navigate = useNavigate();

  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [email, setEmail] = useState('');
  const [user, setUser] = useState('');
  const [phoneNumber, setPhoneNumber] = useState('');
  const [address, setAddress] = useState('');
  const [gender, setGender] = useState('');

  const [validName, setValidName] = useState(false);
  const [validEmail, setValidEmail] = useState(false);
  const [validPhone, setValidPhone] = useState(false);
  const [validPassword, setValidPassword] = useState(false);
  const [validMatch, setValidMatch] = useState(false);

  const [password, setPassword] = useState('');
  const [matchPassword, setMatchPassword] = useState('');

  const [userFocus, setUserFocus] = useState(false);
  const [passwordFocus, setPasswordFocus] = useState(false);
  const [matchFocus, setMatchFocus] = useState(false);

  const [errMsg, setErrMsg] = useState('');
  const [success, setSuccess] = useState(false);

  const saveCustomerRegistration = (e) => {
    e.preventDefault();
    const customerData = {
      username: user,
      password,
      firstName,
      lastName,
      email,
      address,
      phoneNumber,
      gender: gender.toUpperCase()
    };
    AuthService.registerCustomer(customerData).then(response => {
      console.log("The response is received from register-customer api ", response.data);
      navigate('/customer-login');
    }).catch(error => {
      console.log(error);
    });
  };

  useEffect(() => {
    userRef.current.focus();
    console.log("First useEffect(). Focus set on user input.");
  }, []);

  useEffect(() => {
    const result = USER_REGEX.test(user);
    setValidName(result);
    console.log("Second useEffect().");
  }, [user]);

  useEffect(() => {
    const resultEmail = EMAIL_REGEX.test(email);
    setValidEmail(resultEmail);
    console.log("Third useEffect().");
  }, [email]);

  useEffect(() => {
    const resultPhone = PHONE_REGEX.test(phoneNumber);
    setValidPhone(resultPhone);
    console.log("Fourth useEffect().");
  }, [phoneNumber]);

  useEffect(() => {
    const result = PWD_REGEX.test(password);
    setValidPassword(result);
    setValidMatch(password === matchPassword);
    console.log("Fifth useEffect().");
  }, [password, matchPassword]);

  useEffect(() => {
    setErrMsg('');
    console.log("Sixth useEffect().");
  }, [user, password, matchPassword, email, phoneNumber]);

  return (
    <div>
      <Header />
      <div className='customer-register-outer-div'>
        <div className='customer-register-main-div'>
          <section className='customer-register-section'>
            <p ref={errorRef} className={errMsg ? "errmsg" : "offscreen"} aria-live="assertive">
              {errMsg}
            </p>
            <h1 className='customer-register-heading'>Customer Registration</h1>

            <form className='customer-register-form' onSubmit={saveCustomerRegistration}>
              <label className='customer-register-label' htmlFor="username">
                Username
                <span className="required">*</span>
                <span className={validName ? "valid" : "hide"}>
                  <FontAwesomeIcon icon={faCheck} />
                </span>
                <span className={validName || user ? "hide" : "invalid"}>
                  <FontAwesomeIcon icon={faTimes} />
                </span>
              </label>
              <input
                className='customer-register-username'
                type="text"
                id="username"
                ref={userRef}
                autoComplete="off"
                onChange={(e) => setUser(e.target.value)}
                required
                aria-invalid={validName ? "false" : "true"}
                aria-describedby="uidnote"
                onFocus={() => setUserFocus(true)}
                onBlur={() => setUserFocus(false)}
              />
              <p id="uidnote" className={userFocus && user && !validName ? "instructions" : "offscreen"}>
                <FontAwesomeIcon icon={faInfoCircle} />
                4 to 24 characters. Must begin with a letter. Letters, Numbers, Underscores, Hyphen allowed.
              </p>

              <label className='customer-register-label' htmlFor="firstName">
                First Name<span className="required">*</span>
              </label>
              <input
                className='customer-register-firstname'
                type="text"
                id="firstName"
                onChange={(e) => setFirstName(e.target.value)}
                required
              />

              <label className='customer-register-label' htmlFor="lastName">
                Last Name<span className="required">*</span>
              </label>
              <input
                className='customer-register-lastname'
                type="text"
                id="lastName"
                onChange={(e) => setLastName(e.target.value)}
                required
              />

              <label className='customer-register-label' htmlFor="email">
                Email<span className="required">*</span>
                <span className={validEmail ? "valid" : "hide"}>
                  <FontAwesomeIcon icon={faCheck} />
                </span>
                <span className={validEmail || email ? "hide" : "invalid"}>
                  <FontAwesomeIcon icon={faTimes} />
                </span>
              </label>
              <input
                className='customer-register-email'
                type="email"
                id="email"
                onChange={(e) => setEmail(e.target.value)}
                required
                aria-invalid={validEmail ? "false" : "true"}
                aria-describedby="emailnote"
              />
              <p id="emailnote" className={email && !validEmail ? "instructions" : "offscreen"}>
                <FontAwesomeIcon icon={faInfoCircle} />
                Please enter a valid email address.
              </p>

              <label className='customer-register-label' htmlFor="phoneNumber">
                Phone Number<span className="required">*</span>
                <span className={validPhone ? "valid" : "hide"}>
                  <FontAwesomeIcon icon={faCheck} />
                </span>
                <span className={validPhone || phoneNumber ? "hide" : "invalid"}>
                  <FontAwesomeIcon icon={faTimes} />
                </span>
              </label>
              <input
                className='customer-register-phone'
                type="text"
                id="phoneNumber"
                onChange={(e) => setPhoneNumber(e.target.value)}
                required
                pattern="[0-9]{10}"
                title="Phone number must be 10 digits long"
                aria-invalid={validPhone ? "false" : "true"}
                aria-describedby="phonenote"
              />
              <p id="phonenote" className={phoneNumber && !validPhone ? "instructions" : "offscreen"}>
                <FontAwesomeIcon icon={faInfoCircle} />
                Please enter a valid 10-digit phone number.
              </p>

              <label className='customer-register-label' htmlFor="address">
                Address<span className="required">*</span>
              </label>
              <input
                className='customer-register-address'
                type="text"
                id="address"
                onChange={(e) => setAddress(e.target.value)}
                required
              />

              <label className='customer-register-label' htmlFor="gender">
                Gender<span className="required">*</span>
              </label>
              <select className='customer-register-dropdown' id="gender" onChange={(e) => setGender(e.target.value.toUpperCase())} required>
                <option value="">Select Gender</option>
                <option value="MALE">Male</option>
                <option value="FEMALE">Female</option>
                <option value="OTHER">Other</option>
              </select>

              <label className='customer-register-label' htmlFor="password">
                Password<span className="required">*</span>
                <span className={validPassword ? "valid" : "hide"}>
                  <FontAwesomeIcon icon={faCheck} />
                </span>
                <span className={validPassword || password ? "hide" : "invalid"}>
                  <FontAwesomeIcon icon={faTimes} />
                </span>
              </label>
              <input
                className='customer-register-password'
                type="password"
                id="password"
                onChange={(e) => setPassword(e.target.value)}
                value={password}
                required
                aria-invalid={validPassword ? "false" : "true"}
                aria-describedby="pwdnote"
                onFocus={() => setPasswordFocus(true)}
                onBlur={() => setPasswordFocus(false)}
              />
              <p id="pwdnote" className={passwordFocus && !validPassword ? "instructions" : "offscreen"}>
                <FontAwesomeIcon icon={faInfoCircle} />
                8 to 24 characters. Must include uppercase, lowercase letters, a number, and a special symbol.
              </p>

              <label className='customer-register-label' htmlFor="confirm-pwd">
                Confirm Password<span className="required">*</span>
                <span className={validMatch && matchPassword ? "valid" : "hide"}>
                  <FontAwesomeIcon icon={faCheck} />
                </span>
                <span className={validMatch || matchPassword ? "hide" : "invalid"}>
                  <FontAwesomeIcon icon={faTimes} />
                </span>
              </label>
              <input
                className='customer-register-confirm'
                type="password"
                id="confirm-pwd"
                onChange={(e) => setMatchPassword(e.target.value)}
                value={matchPassword}
                required
                aria-invalid={validMatch ? "false" : "true"}
                aria-describedby="confirmnote"
                onFocus={() => setMatchFocus(true)}
                onBlur={() => setMatchFocus(false)}
              />
              <p id="confirmnote" className={matchFocus && !validMatch ? "instructions" : "offscreen"}>
                <FontAwesomeIcon icon={faInfoCircle} />
                Must match the first password input field
              </p><br />

              <button className='customer-register-submit' disabled={!validName || !validPassword || !validMatch || !validPhone || !validEmail || !address || !gender} onClick={saveCustomerRegistration}>
                Sign Up
              </button>
            </form>

            <p className='customer-register-paragraph'>
              Already Registered?
              <span className="customer-register-span">
                <Link className='customer-register-link' to="/customer-login">Sign In</Link>
              </span>
            </p>
          </section>
        </div>
      </div>
    </div>
  );
};
