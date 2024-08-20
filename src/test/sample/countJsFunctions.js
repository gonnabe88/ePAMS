const fs = require('fs');
const path = require('path');

// JavaScript 파일에서 함수 선언과 함수 표현식을 찾기 위한 정규식
const functionPattern = /function\s+\w+\s*\(|\w+\s*=\s*function\s*\(|\(\)\s*=>|\w+\s*:\s*function\s*\(/g;

function countFunctionsInFile(filePath) {
    const content = fs.readFileSync(filePath, 'utf-8');
    const matches = content.match(functionPattern);
    return matches ? matches.length : 0;
}

function scanDirectory(directory) {
    let totalFunctionCount = 0;

    const files = fs.readdirSync(directory);

    files.forEach(file => {
        const fullPath = path.join(directory, file);
        const stat = fs.statSync(fullPath);

        if (file.endsWith('.js')) {
            // JavaScript 파일이라면 함수 수를 세도록 함
            const functionCount = countFunctionsInFile(fullPath);
            console.log(`${fullPath}: ${functionCount} functions`);
            totalFunctionCount += functionCount;
        }
    });

    return totalFunctionCount;
}

// 경로를 올바르게 수정
const totalFunctions = scanDirectory('D:/workspace/ePAMS/src/main/resources/static/js');
console.log(`Total number of functions: ${totalFunctions}`);
