

describe('Test main', () => {
    const {FnMkdir, FnProgrmMkdir} = require('../main_test');

    test('FnHwInfoMkdir function', async() => {
        expect(FnProgrmMkdir()).toBeDefined();
    });

   
 });