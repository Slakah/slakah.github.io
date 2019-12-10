// See https://docusaurus.io/docs/site-config.html for all the possible
// site configuration options.

const repoUrl = 'https://github.com/slakah'

const siteConfig = {
  title: 'Gubbns', // Title for your website.
  tagline: 'Stuff to share',
  twitterUsername: 'JRCCollier',
  url: 'https://slakah.github.io', // Your website URL
  baseUrl: '/', // Base URL for your project */

  // Used for publishing and more
  projectName: 'slakah.github.io',
  organizationName: 'slakah',

  // For no header links in the top nav bar -> headerLinks: [],
  headerLinks: [
    { blog: true, label: 'Blog' },
    { doc: 'talks', label: 'Talks' },
    { page: 'projects', label: 'Projects' },
    { href: repoUrl, label: "GitHub", external: true },
  ],

  /* path to images for header/footer */
  headerIcon: 'img/favicon.svg',
  footerIcon: 'img/favicon.svg',
  favicon: 'img/favicon.png',

  /* Colors for website */
  colors: {
    primaryColor: '#d63417',
    secondaryColor: '#7c2617',
  },

  // This copyright info is used in /core/Footer.js and blog RSS/Atom feeds.
  copyright: `Copyright © ${new Date().getFullYear()} James Collier`,

  highlight: {
    // Highlight.js theme to use for syntax highlighting in code blocks.
    theme: 'default',
  },
  // Add custom scripts here that would be placed in <script> tags.
  scripts: ['https://buttons.github.io/buttons.js'],
  // On page navigation for the current documentation page.
  onPageNav: 'separate',
  // No .html extensions for paths.
  cleanUrl: true,
  // Open Graph and Twitter card images.
  ogImage: 'img/undraw_online.svg',
  twitterImage: 'img/undraw_tweetstorm.svg',

  repoUrl: repoUrl,
};

module.exports = siteConfig;
