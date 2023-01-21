/*시스템 설정에 따라 다크모드 전환 가능*/
const isBrowserDarkMode = window.matchMedia('(prefers-color-scheme: dark)').matches
let initTheme = isBrowserDarkMode ? 'dark' : 'light'

const editor = new toastui.Editor({
    el: document.querySelector('#editor'),
    previewStyle: 'vertical',
    height: 'auto',
    minHeight: '600px',
    initialValue: '',
    theme: initTheme,
});