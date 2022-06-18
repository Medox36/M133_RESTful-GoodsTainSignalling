/**
 * utility functions for multiple pages
 *
 * @author  Marcel Suter
 * @since   2022-05-30
 * @version 1.0
 */

/**
 * get the value of an url parameter identified by key
 * source: https://www.sitepoint.com/get-url-parameters-with-javascript/
 * @param key  the key to be searched
 * @returns values as a String or null
 */
function getQueryParam (key) {
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);

    return urlParams.get(key);
}