

describe('Test FnHwInfoMkdir', () => {
    const {FnHwInfoMkdir, FnProgrmMkdir} = require('./FnMkdir');

    test('FnHwInfoMkdir function', async() => {
        expect(FnHwInfoMkdir()).toBeTruthy();
    });

    test('FnProgrmMkdir function', async() => {
        expect(FnProgrmMkdir()).toBeTruthy();
    });
 });

//  describe('Test FnProgrmMkdir', () => {
//     const FnProgrmMkdir = require('./FnMkdir');
//     test('FnProgrmMkdir function', async() => {
//         expect(FnProgrmMkdir()).toBeTruthy();
//     });
//  });