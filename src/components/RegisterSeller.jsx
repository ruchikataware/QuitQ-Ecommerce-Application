import React, { useEffect, useRef, useState } from 'react';
import './register-seller.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCheck, faInfoCircle, faTimes } from '@fortawesome/free-solid-svg-icons';
import { Link, useNavigate } from 'react-router-dom';
import Header from './Header';
import AuthService from '../services/AuthService';

const USER_REGEX = /^[a-zA-Z][a-zA-Z0-9-_]{3,23}$/;
const PWD_REGEX = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%]).{8,24}$/;
const PHONE_REGEX = /^[0-9]{10}$/;
const EMAIL_REGEX = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

export const RegisterSellerComponent = () => {
  console.log("RegisterSellerComponent() is called..");
  const userRef = useRef();
  const errorRef = useRef();
  const navigate = useNavigate();

  const [storeName, setStoreName] = useState('');
  const [businessAddress, setBusinessAddress] = useState('');
  const [businessRegistrationNumber, setBusinessRegistrationNumber] = useState('');
  const [contactPersonName, setContactPersonName] = useState('');
  const [contactPersonPhone, setContactPersonPhone] = useState('');
  const [username, setUsername] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [matchPassword, setMatchPassword] = useState('');

  const [validName, setValidName] = useState(false);
  const [validEmail, setValidEmail] = useState(false);
  const [validPassword, setValidPassword] = useState(false);
  const [validMatch, setValidMatch] = useState(false);
  const [validPhone, setValidPhone] = useState(false);

  const [userFocus, setUserFocus] = useState(false);
  const [passwordFocus, setPasswordFocus] = useState(false);
  const [matchFocus, setMatchFocus] = useState(false);

  const [errMsg, setErrMsg] = useState('');
  const [success, setSuccess] = useState(false);

  const saveSellerRegistration = (e) => {
    console.log("saveSellerRegistration() is called..");
    e.preventDefault();
    const sellerData = {
      username,
      email,
      password,
      storeName,
      businessAddress,
      businessRegistrationNumber,
      contactPersonName,
      contactPersonPhone
    };
    AuthService.registerSeller(sellerData).then(response => {
      console.log("The response from register-seller is received ", response.data);
      navigate('/seller-login');
    })
  };

  useEffect(() => {
    console.log("(Seller) First useEffect().");
    userRef.current.focus();
  }, []);

  useEffect(() => {
    console.log("(Seller) Second useEffect().");
    setValidName(USER_REGEX.test(username));
  }, [username]);

  useEffect(() => {
    console.log("(Seller) Third useEffect().");
    setValidEmail(EMAIL_REGEX.test(email));
  }, [email]);

  useEffect(() => {
    console.log("(Seller) Fourth useEffect().");
    setValidPassword(PWD_REGEX.test(password));
    setValidMatch(password === matchPassword);
  }, [password, matchPassword]);

  useEffect(() => {
    console.log("(Seller) Fifth useEffect().");
    setValidPhone(PHONE_REGEX.test(contactPersonPhone));
  }, [contactPersonPhone]);

  useEffect(() => {
    console.log("(Seller) Sixth useEffect().");
    setErrMsg('');
  }, [username, password, matchPassword, email, contactPersonPhone]);

  return (
    <div>
      <Header />
      <div className='seller-register-outer-div'>
        <div className='seller-register-main-div'>
          <section className='seller-register-section'>
            <p ref={errorRef} className={errMsg ? "errmsg" : "offscreen"} aria-live="assertive">
              {errMsg}
            </p>
            <h1 className='seller-register-heading'>Seller Registration</h1>

            <form className='seller-register-form' onSubmit={saveSellerRegistration}>
              <label className='seller-register-label' htmlFor="username">
                Username<span className="required">*</span>
                <span className={validName ? "valid" : "hide"}>
                  <FontAwesomeIcon icon={faCheck} />
                </span>
                <span className={validName || username ? "hide" : "invalid"}>
                  <FontAwesomeIcon icon={faTimes} />
                </span>
              </label>
              <input
                className='seller-register-username'
                type="text"
                id="username"
                ref={userRef}
                autoComplete="off"
                onChange={(e) => setUsername(e.target.value)}
                required
                aria-invalid={validName ? "false" : "true"}
                aria-describedby="uidnote"
                onFocus={() => setUserFocus(true)}
                onBlur={() => setUserFocus(false)}
              />

              <p id="uidnote" className={userFocus && username && !validName ? "instructions" : "offscreen"}>
                <FontAwesomeIcon icon={faInfoCircle} />
                4 to 24 characters. Must begin with a letter. Letters, Numbers, Underscores, Hyphen allowed.
              </p>

              <label className='seller-register-label' htmlFor="storeName">
                Store Name<span className="required">*</span>
              </label>
              <input
                className='seller-register-storeName'
                type="text"
                id="storeName"
                onChange={(e) => setStoreName(e.target.value)}
                required
              />

              <label className='seller-register-label' htmlFor="businessAddress">
                Business Address<span className="required">*</span>
              </label>
              <input
                className='seller-register-businessAddress'
                type="text"
                id="businessAddress"
                onChange={(e) => setBusinessAddress(e.target.value)}
                required
              />

              <label className='seller-register-label' htmlFor="businessRegistrationNumber">
                Business Registration Number<span className="required">*</span>
              </label>
              <input
                className='seller-register-businessRegistrationNumber'
                type="text"
                id="businessRegistrationNumber"
                onChange={(e) => setBusinessRegistrationNumber(e.target.value)}
                required
              />

              <label className='seller-register-label' htmlFor="contactPersonName">
                Contact Person Name<span className="required">*</span>
              </label>
              <input
                className='seller-register-contactPersonName'
                type="text"
                id="contactPersonName"
                onChange={(e) => setContactPersonName(e.target.value)}
                required
              />

              <label className='seller-register-label' htmlFor="contactPersonPhone">
                Contact Person Phone<span className="required">*</span>
                <span className={validPhone ? "valid" : "hide"}>
                  <FontAwesomeIcon icon={faCheck} />
                </span>
                <span className={validPhone || contactPersonPhone ? "hide" : "invalid"}>
                  <FontAwesomeIcon icon={faTimes} />
                </span>
              </label>
              <input
                className='seller-register-contactPersonPhone'
                type="text"
                id="contactPersonPhone"
                onChange={(e) => setContactPersonPhone(e.target.value)}
                required
                pattern="[0-9]{10}"
                title="Phone number must be 10 digits long"
                aria-invalid={validPhone ? "false" : "true"}
                aria-describedby="phonenote"
              />

              <p id="phonenote" className={contactPersonPhone && !validPhone ? "instructions" : "offscreen"}>
                <FontAwesomeIcon icon={faInfoCircle} />
                Please enter a valid 10-digit phone number.
              </p>

              <label className='seller-register-label' htmlFor="email">
                Email<span className="required">*</span>
                <span className={validEmail ? "valid" : "hide"}>
                  <FontAwesomeIcon icon={faCheck} />
                </span>
                <span className={validEmail || email ? "hide" : "invalid"}>
                  <FontAwesomeIcon icon={faTimes} />
                </span>
              </label>
              <input
                className='seller-register-email'
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

              <label className='seller-register-label' htmlFor="password">
                Password<span className="required">*</span>
                <span className={validPassword ? "valid" : "hide"}>
                  <FontAwesomeIcon icon={faCheck} />
                </span>
                <span className={validPassword || password ? "hide" : "invalid"}>
                  <FontAwesomeIcon icon={faTimes} />
                </span>
              </label>
              <input
                className='seller-register-password'
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

              <label className='seller-register-label' htmlFor="confirm-pwd">
                Confirm Password<span className="required">*</span>
                <span className={validMatch && matchPassword ? "valid" : "hide"}>
                  <FontAwesomeIcon icon={faCheck} />
                </span>
                <span className={validMatch || matchPassword ? "hide" : "invalid"}>
                  <FontAwesomeIcon icon={faTimes} />
                </span>
              </label>
              <input
                className='seller-register-confirm'
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

              <button
                className='seller-register-submit'
                disabled={!validName || !validPassword || !validMatch || !validEmail || !storeName || !businessAddress || !businessRegistrationNumber || !contactPersonName || !validPhone} onClick={saveSellerRegistration}
              >
                Sign Up
              </button>
            </form>

            <p className='seller-register-paragraph'>
              Already Registered?
              <span className="seller-register-span">
                <Link className='seller-register-link' to="/seller-login">Sign In</Link>
              </span>
            </p>
          </section>
        </div>
      </div>
    </div>
  );
};
